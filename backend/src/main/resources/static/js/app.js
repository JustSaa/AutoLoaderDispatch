/* app.js */

/* ========== МОДУЛЬ: auth ========== */
const auth = {
  async login(username, password) {
    const res = await fetch('/api/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password })
    });
    if (!res.ok) throw await res.text();
    const { accessToken, refreshToken } = await res.json();
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
  },

  logout() {
    localStorage.clear();
    location.href = '/login.html';
  },

  get accessToken() {
    return localStorage.getItem('accessToken');
  },

  get refreshToken() {
    return localStorage.getItem('refreshToken');
  },

  ensureAuthenticated() {
    if (!this.accessToken) {
      this.logout();
      throw new Error('Not authenticated');
    }
  }
};

/* ========== МОДУЛЬ: api ========== */
const api = {
  async request(path, opts = {}) {
    opts.headers = opts.headers || {};
    auth.ensureAuthenticated();
    opts.headers['Authorization'] = 'Bearer ' + auth.accessToken;

    let res = await fetch(path, opts);
    if (res.status === 401) {
      // accessToken expired — try refresh
      const rtRes = await fetch('/api/auth/refresh', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ refreshToken: auth.refreshToken })
      });
      if (!rtRes.ok) {
        auth.logout();
      }
      const { accessToken } = await rtRes.json();
      localStorage.setItem('accessToken', accessToken);
      // retry original
      opts.headers['Authorization'] = 'Bearer ' + accessToken;
      res = await fetch(path, opts);
    }
    if (!res.ok) {
      throw await res.text();
    }
    return res.json();
  },

  // helper endpoints
  fetchWarehouses() {
    return this.request('/api/warehouses');
  },
  fetchRequests() {
    return this.request('/api/requests');
  },
  whoAmI() {
    return this.request('/api/auth/me');
  }
};

let currentUser = null;

/* ========== DASHBOARD ИНИЦИАЛИЗАЦИЯ ========== */
async function initDashboard() {
  // 0) получить информацию о залогиненном пользователе
  currentUser = await api.whoAmI();

  // 1) загрузить склады в селект
  const select = document.getElementById('warehouseSelect');
  try {
    const whs = await api.fetchWarehouses();
    whs.forEach(w => {
      const opt = document.createElement('option');
      opt.value = w.id;
      opt.textContent = w.name;
      select.append(opt);
    });
  } catch (e) {
    console.error('Не удалось загрузить склады:', e);
  }

  // 2) повесить сабмит на форму создания заявки
  const form = document.getElementById('requestForm');
  const errP = document.getElementById('requestError');
  const ul   = document.getElementById('requestsList');

  form.addEventListener('submit', async e => {
    e.preventDefault();
    errP.textContent = '';
    const wid = select.value;
    if (!wid) {
      errP.textContent = 'Пожалуйста, выберите склад.';
      return;
    }
    try {
      const newReq = await api.request('/api/requests', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          user:      { id: currentUser.id },
          warehouse: { id: Number(wid) }
        })
      });
      // отрисовать новую заявку наверху списка
      const li = document.createElement('li');
      li.textContent = `#${newReq.id}: ${newReq.status}, погрузчик: ${newReq.loader?.name || '—'}`;
      ul.prepend(li);
      form.reset();
    } catch (err) {
      errP.textContent = err;
    }
  });

  // 3) первоначально вывести все заявки
  try {
    const reqs = await api.fetchRequests();
    reqs.forEach(r => {
      const li = document.createElement('li');
      li.textContent = `#${r.id}: ${r.status}, погрузчик: ${r.loader?.name || '—'}`;
      ul.append(li);
    });
  } catch (e) {
    console.error('Не удалось загрузить заявки:', e);
  }

  // 4) кнопка выхода
  document.getElementById('logout').addEventListener('click', () => auth.logout());
}

/* ========== DOMContentLoaded ========= */
document.addEventListener('DOMContentLoaded', () => {
  const loginForm = document.getElementById('loginForm');
  if (loginForm) {
    // — Страница логина —
    loginForm.addEventListener('submit', async e => {
      e.preventDefault();
      const u = document.getElementById('username').value.trim();
      const p = document.getElementById('password').value;
      const err = document.getElementById('loginError');
      err.textContent = '';
      try {
        await auth.login(u, p);
        location.href = '/dashboard.html';
      } catch (error) {
        err.textContent = error;
      }
    });
  }

  if (location.pathname.endsWith('dashboard.html')) {
    // — Страница дашборда —
    try {
      auth.ensureAuthenticated();
      initDashboard();
    } catch (e) {
      console.error(e);
    }
  }
});