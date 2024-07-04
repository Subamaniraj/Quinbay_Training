const app = {
  default: {
    locale: 'id'
  },
  routerName: {
    home: 'home',
    about: 'about',
    login:'login',
    signup: 'signup',
    admin:'admin',
    customer:'customer',
  }
}
const pages = {
  home: '/',
  about: '/about',
  login:'/login',
  signup:'/signup',
  admin:'/admin',
  customer:'/customer'
}

const api = {
  testGetAPI: {
    api: '/backend/test'
  },
  testPostAPI: {
    api: '/backend/test'
  }
}

export { app, pages, api }
