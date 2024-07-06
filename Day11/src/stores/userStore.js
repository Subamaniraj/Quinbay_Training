import { defineStore } from 'pinia';

export const useUserStore = defineStore({
  id: 'user',
  state: () => ({
    user: "1234@gmail.com"
  }),
  actions: {
    setUser(newUser) {
      this.user = newUser;
    },
    clearUser() {
      this.user = null;
    }
  }
});
