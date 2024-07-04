<template>
  <div class="auth-container">
    <h2>Signup</h2>
    <form @submit.prevent="signup">
      <label for="email">Email:</label>
      <input type="email" v-model="email" required>
      <label for="password">Password:</label>
      <input type="password" v-model="password" required>
      <button type="submit">Signup</button>
    </form>
    <p>Already have an account? <router-link to="/login">Login</router-link></p>
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
    signup() {
      const users = JSON.parse(localStorage.getItem('users')) || [];
      users.push({ email: this.email, password: this.password });
      localStorage.setItem('users', JSON.stringify(users));
      this.$router.push('/login');
    },
  },
};
</script>

<style scoped>
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
