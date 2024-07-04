<template>
  <div>
    <Header :searchQuery="searchQuery" @update:searchQuery="filterProducts"/>
    <div class="main-container">
      <AddProduct @product-added="fetchProducts"/>
      <ProductList :products="products" :searchQuery="searchQuery" :addToCart="addToCart"/>
      <Cart :cartItems="cartItems" @remove-from-cart="removeFromCart" @checkout="checkout"/>
      </div>
      <div>
      <Footer/>
    </div>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import AddProduct from '@/components/AddProduct.vue';
import ProductList from '@/components/ProductList.vue';
import Cart from '@/components/Cart.vue';
import Footer from '@/components/Footer.vue';

export default {
  components: {
    Header,
    AddProduct,
    ProductList,
    Cart,
    Footer,
  },
  data() {
    return {
      products: [],
      searchQuery: '',
      cartItems: [],
    };
  },
  methods: {
    fetchProducts() {
      this.products = JSON.parse(localStorage.getItem('products')) || [];
    },
    filterProducts(query) {
      this.searchQuery = query;
    },
    addToCart(product) {
      const cartItem = this.cartItems.find(item => item.product.title === product.title);
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

<style scoped>
.main-container {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}
</style>
