<template>
  <div class="form-container">
    <h4>Add Product</h4>
    <label for="name">Name:</label>
    <input type="text" v-model="name">
    <label for="stock">Stock:</label>
    <input type="number" v-model="stock">
    <label for="price">Price:</label>
    <input type="number" v-model="price">
    <label for="categoryId">Category ID:</label>
    <input type="number" v-model="categoryId">
    <label for="sellerId">Seller ID:</label>
    <input type="number" v-model="sellerId">
    <button @click="addProduct">Add Product</button>
  </div>
</template>

<script>
import API from '@/api/test-api.js';
import { api } from '@/config';

export default {
  data() {
    return {
      name: '',
      stock: 0,
      price: 0,
      categoryId: 0,
      sellerId: 0,
    };
  },
  methods: {
    async addProduct() {
      const product = {
        name: this.name,
        stock: this.stock,
        price: this.price,
        categoryId: this.categoryId,
        sellerId: this.sellerId,
      };

      try {
        const response = await API.postDataViaAPI(api.addProduct, product);
        console.log('Product added:', response);
        this.$emit('product-added');
        this.resetForm();
      } catch (error) {
        console.error('Error adding product:', error);
      }
    },
    resetForm() {
      this.name = '';
      this.stock = 0;
      this.price = 0;
      this.categoryId = 0;
      this.sellerId = 0;
    },
  },
};
</script>

<style lang="scss" scoped>
.form-container {
  position: sticky;
  display: flex;
  flex-direction: column;
  width: 20%;
  margin-right: 20px;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  top: 50px;
  height: calc(100vh - 95px);
}

.form-container h4 {
  margin-bottom: 20px;
  color: #4a148c;
}

.form-container label,
.form-container input,
.form-container button {
  margin-bottom: 15px;
}

.form-container input {
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.form-container button {
  padding: 10px;
  border-radius: 5px;
  border: none;
  background-color: #3085ff;
  color: white;
  cursor: pointer;
}
</style>