<template>
  <header class="header">
    <h2><b>Blibli</b></h2>
    <input type="search" :value="searchQuery" @input="$emit('update:searchQuery', $event.target.value)" placeholder="Search...">
    <i class="cart-icon" @click="toggleCartPopup">🛒</i>
  </header>
  <div v-if="isCartPopupVisible" class="cart-popup">
    <Cart :cartItems="cartItems" @remove-from-cart="removeFromCart" @checkout="checkout" />
  </div>
</template>

<script>
import Cart from '@/components/Cart.vue';

export default {
  components: {
    Cart,
  },
  props: {
    searchQuery: String,
    cartItems: Array,
  },
  data() {
    return {
      isCartPopupVisible: false,
    };
  },
  methods: {
    toggleCartPopup() {
      this.isCartPopupVisible = !this.isCartPopupVisible;
    },
    removeFromCart(index) {
      this.$emit('remove-from-cart', index);
    },
    checkout() {
      this.$emit('checkout');
    },
  },
};
</script>

<style lang="scss" scoped>
.header {
  position: sticky;
  top: 0;
  background-color: #3085ff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  z-index: 1000;
  color: white;
}

.header input {
  border-radius: 8px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 40%;
}

.cart-icon {
  font-size: 24px;
  cursor: pointer;
}

.cart-popup {
  position: fixed;
  top: 60px;
  right: 20px;
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  z-index: 1001;
}
</style>
