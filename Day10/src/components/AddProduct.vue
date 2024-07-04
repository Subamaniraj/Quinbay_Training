<template>
  <div class="form-container">
    <h4>Add Product</h4>
    <label for="image">Image URL:</label>
    <input type="url" v-model="image">
    <label for="title">Title:</label>
    <input type="text" v-model="title">
    <label for="brand">Brand:</label>
    <input type="text" v-model="brand">
    <label for="price">Price:</label>
    <input type="number" v-model="price">
    <label for="ram">RAM:</label>
    <input type="text" v-model="ram">
    <label for="rom">ROM:</label>
    <input type="text" v-model="rom">
    <label for="quantity">Quantity:</label>
    <input type="number" v-model="quantity">
    <button @click="addProduct">Add Product</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      image: '',
      title: '',
      brand: '',
      price: 0,
      ram: '',
      rom: '',
      quantity: 0,
    };
  },
  methods: {
    addProduct() {
      const product = {
        image: this.image,
        title: this.title,
        brand: this.brand,
        price: parseFloat(this.price),
        ram: this.ram,
        rom: this.rom,
        quantity: parseInt(this.quantity, 10),
      };
      let products = JSON.parse(localStorage.getItem('products')) || [];
      products.push(product);
      localStorage.setItem('products', JSON.stringify(products));
      this.$emit('product-added');
      this.image = '';
      this.title = '';
      this.brand = '';
      this.price = 0;
      this.ram = '';
      this.rom = '';
      this.quantity = 0;
    },
  },
};
</script>

<style scoped>
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
