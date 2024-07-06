<template>
  <div class="cards-container">
    <div v-for="(product, index) in products" :key="index" class="card">
      <img src="https://static.bhphoto.com/images/images250x250/1682110530000_1762582.jpg" :alt="product.name">
      <div class="card-content">
        <h3 class="card-title">{{ product.name }}</h3>
        <p class="card-stock">Stock: {{ product.stock }}</p>
        <p class="card-price">Price: ₹{{ product.price.toFixed(2) }}</p>
        <p class="card-categoryId">Category ID: {{ product.categoryId }}</p>
        <p class="card-sellerId">Seller ID: {{ product.sellerId }}</p>
        <button class="add-to-cart-btn" @click="addToCartAndUpdate(product)">Add to Cart</button>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '@/config';
import API from '@/api/test-api.js';

export default {
  props: {
    addToCart: Function,
  },
  data() {
    return {
      products: [],
    };
  },
  created() {
    this.fetchProducts();
  },
  methods: {
    async fetchProducts() {
      try {
        const response = await API.getDataByAPI(api.getAllProducts);
        this.products = response.body || []; 
      } catch (error) {
        console.error('Error fetching products:', error);
      }
    },
    addToCartAndUpdate(product) {
      this.addToCart(product);
      product.stock--;
      this.updateProductQuantity(product);
    },
    async updateProductQuantity(product) {
      try {
        await API.postDataViaAPI({
          url: `http://www.localhost:8085/inventory/product/update/${product.id}`,
          body: { stock: product.stock },
        });
      } catch (error) {
        console.error('Error updating product quantity:', error);
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.cards-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
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
  object-fit:cover;
}

.card-content {
  padding: 15px;
}

.card-title {
  font-size: 20px;
  margin: 0 0 10px;
  color: #3085ff;
}

.card-stock,
.card-price,
.card-categoryId,
.card-sellerId {
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
