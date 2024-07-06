<template>
  <div class="auth-container">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <label for="email">Email:</label>
      <input type="email" v-model="email" required>
      <label for="password">Password:</label>
      <input type="password" v-model="password" required>
      <button type="submit">Login</button>
    </form>
    <p>Don't have an account? <router-link to="/signup" >Signup</router-link></p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: '',
    };
  },
  methods: {
    login() {
      const users = JSON.parse(localStorage.getItem('users')) || [];
      const user = users.find(user => user.email === this.email && user.password === this.password);
      if (user) {
        localStorage.setItem('currentUser', JSON.stringify(user));
        if (this.email.includes('admin')) {
          this.$router.push('/admin');
        } else {
          this.$router.push('/customer');
        }
      } else {
        alert('Invalid email or password');
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.auth-container {
  width: 300px;
  margin: auto;
  padding: 20px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.auth-container h2 {
  margin-bottom: 20px;
  color: #4a148c;
}

.auth-container label,
.auth-container input,
.auth-container button {
  display: block;
  width: 100%;
  margin-bottom: 15px;
}

.auth-container input {
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.auth-container button {
  padding: 10px;
  border-radius: 5px;
  border: none;
  background-color: #3085ff;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

.auth-container button:hover {
  background-color: #0749a5;
}
</style>