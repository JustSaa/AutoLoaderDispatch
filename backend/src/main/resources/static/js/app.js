/* ========== МОДУЛЬ: auth ========== */
const auth = {
  async login(username, password) {
    const res = await fetch('/api/auth/login', {
      method: 'POST',
      headers: {'Content-Type':'application/json'},
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

  /* Если токен не найден — редиректим */
  ensureAuthenticated() {
    if (!this.accessToken) {
      location.href = '/login.html';
      throw new Error('Не авторизован');
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
      // пробуем обновить токен
      const rtRes = await fetch('/api/auth/refresh', {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({ refreshToken: auth.refreshToken })
      });
      if (!rtRes.ok) {
        auth.logout(); // и выбрасываем на логин
      }
      const { accessToken } = await rtRes.json();
      localStorage.setItem('accessToken', accessToken);
      // повторяем исходный запрос
      opts.headers['Authorization'] = 'Bearer ' + accessToken;
      res = await fetch(path, opts);
    }
    if (!res.ok) {
      throw await res.text();
    }
    return res.json();
  },

  fetchRequests() {
    return this.request('/api/requests');
  }
};

/* ========== ИНИЦИАЛИЗАЦИЯ СТРАНИЦ ========== */
document.addEventListener('DOMContentLoaded', () => {
  // Страница входа
  const loginForm = document.getElementById('loginForm');
  if (loginForm) {
    loginForm.addEventListener('submit', async e => {
      e.preventDefault();
      const u = document.getElementById('username').value.trim();
      const p = document.getElementById('password').value;
      try {
        await auth.login(u, p);
        location.href = '/dashboard.html';
      } catch (err) {
        document.getElementById('error').textContent = err;
      }
    });
  }

  // Дашборд
  if (location.pathname.endsWith('dashboard.html')) {
    auth.ensureAuthenticated();
    const ul = document.getElementById('requestsList');
    api.fetchRequests()
      .then(requests => {
        requests.forEach(r => {
          const li = document.createElement('li');
          const loaderName = r.loader?.name || '—';
          li.textContent = `#${r.id}: ${r.status}, погрузчик: ${loaderName}`;
          ul.append(li);
        });
      })
      .catch(err => {
        ul.textContent = 'Ошибка загрузки: ' + err;
      });

    document.getElementById('logout').addEventListener('click', () => auth.logout());
  }
});