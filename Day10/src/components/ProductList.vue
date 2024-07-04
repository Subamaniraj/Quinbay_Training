<template>
  <div class="cards-container">
    <div v-for="(product, index) in filteredProducts" :key="index" class="card">
      <img :src="product.image" :alt="product.title">
      <div class="card-content">
        <h3 class="card-title">{{ product.title }}</h3>
        <p class="card-brand">Brand: {{ product.brand }}</p>
        <p class="card-price">Price: ₹{{ product.price.toFixed(2) }}</p>
        <p class="card-ram">RAM: {{ product.ram }}</p>
        <p class="card-rom">ROM: {{ product.rom }}</p>
        <p class="card-quantity">Available: {{ product.quantity }}</p>
        <button class="add-to-cart-btn" @click="addToCartAndUpdate(product)">Add to Cart</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    products: Array,
    addToCart: Function,
  },
  computed: {
    filteredProducts() {
      return this.products;
    }
  },
  methods: {
    addToCartAndUpdate(product) {
      this.addToCart(product);
      product.quantity--; // Reduce the available quantity
      this.updateProductQuantity(product);
    },
    updateProductQuantity(product) {
      let products = JSON.parse(localStorage.getItem('products')) || [];
      const index = products.findIndex(p => p.title === product.title);
      if (index !== -1) {
        products[index].quantity = product.quantity;
        localStorage.setItem('products', JSON.stringify(products));
      }
    },
  },
};
</script>

<style scoped>
.cards-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.card {
  width: 100%;
  border: 1px solid #ddd;
  border-radius: 7px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: white;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.card:hover {
  transform: translateY(-10px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.card img {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.card-content {
  padding: 15px;
}

.card-title {
  font-size: 20px;
  margin: 0 0 10px;
  color: #3085ff;
}

.card-brand,
.card-price,
.card-ram,
.card-rom,
.card-quantity {
  font-size: 16px;
  color: #666;
  margin-bottom: 5px;
}

.card-price {
  font-weight: bold;
  color: #000;
}

.add-to-cart-btn {
  padding: 10px;
  border-radius: 5px;
  border: none;
  background-color: #3085ff;
  color: white;
  cursor: pointer;
  width: 100%;
  text-align: center;
  transition: background-color 0.3s;
}

.add-to-cart-btn:hover {
  background-color: #0749a5;
}
</style>
