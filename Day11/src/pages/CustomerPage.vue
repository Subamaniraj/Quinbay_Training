<template>
  <div>
    <Header :searchQuery="searchQuery" @update:searchQuery="filterProducts" :cartItems="cartItems" @remove-from-cart="removeFromCart" @checkout="checkout"/>
    <div class="main-container">
      <ProductList :products="filteredProducts" :addToCart="addToCart"/>
    </div>
    <Footer/>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import ProductList from '@/components/ProductList.vue';
import Footer from '@/components/Footer.vue';
import axios from 'axios';

export default {
  components: {
    Header,
    ProductList,
    Footer,
  },
  data() {
    return {
      products: [],
      searchQuery: '',
      cartItems: [],
    };
  },
  computed: {
    filteredProducts() {
      return this.products.filter(product => 
        product.title.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
  },
  methods: {
    async fetchProducts() {
      try {
        const response = await axios.get('http://www.localhost:8087/inventory/product/getAllProducts');
        this.products = response.data;
      } catch (error) {
        console.error('Error fetching products:', error);
      }
    },
    filterProducts(query) {
      this.searchQuery = query;
    },
    addToCart(product) {
      const cartItem = this.cartItems.find(item => item.product.id === product.id);
      if (cartItem) {
        cartItem.quantity++;
      } else {
        this.cartItems.push({ product, quantity: 1 });
      }
      localStorage.setItem('cart', JSON.stringify(this.cartItems));
    },
    removeFromCart(index) {
      this.cartItems.splice(index, 1);
      localStorage.setItem('cart', JSON.stringify(this.cartItems));
    },
    checkout() {
      alert('Checkout successful!');
      this.cartItems = [];
      localStorage.removeItem('cart');
    },
  },
  created() {
    this.fetchProducts();
    this.cartItems = JSON.parse(localStorage.getItem('cart')) || [];
  },
};
</script>

<style lang="scss" scoped>
.main-container {
  display: flex;
  justify-content: center;
  padding: 20px;
}
</style>
