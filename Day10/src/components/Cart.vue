<template>
  <div class="cart-container">
    <h4>Cart</h4>
    <div v-for="(item, index) in cartItems" :key="index" class="cart-item">
      <img :src="item.product.image" :alt="item.product.title">
      <div class="cart-item-details">
        <p>{{ item.product.title }}</p>
        <p>Quantity: {{ item.quantity }}</p>
        <p>Price: ₹{{ (item.product.price * item.quantity).toFixed(2) }}</p>
      </div>
      <button class="remove-btn" @click="removeFromCartAndUpdate(index)">Remove</button>
    </div>
    <div v-if="cartItems.length === 0" class="no-items">
      Your cart is empty.
    </div>
    <button class="checkout-btn" @click="checkoutAndUpdate">Checkout</button>
  </div>
</template>

<script>
export default {
  props: {
    cartItems: Array,
  },
  methods: {
    removeFromCartAndUpdate(index) {
      const removedItem = this.cartItems[index];
      this.$emit('remove-from-cart', index);
      removedItem.product.quantity += removedItem.quantity; // Increase the available quantity
      this.updateProductQuantity(removedItem.product);
    },
    updateProductQuantity(product) {
      let products = JSON.parse(localStorage.getItem('products')) || [];
      const index = products.findIndex(p => p.title === product.title);
      if (index !== -1) {
        products[index].quantity = product.quantity;
        localStorage.setItem('products', JSON.stringify(products));
      }
    },
    checkoutAndUpdate() {
      this.$emit('checkout');
      
      this.cartItems.forEach(cartItem => {
        this.updateProductQuantity(cartItem.product);
      });
      localStorage.removeItem('cart');
    },
  },
};
</script>

<style scoped>
.cart-container {
  width: 25%;
  margin-left: 20px;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 50px; 
  height: calc(100vh - 125px); 
  overflow-y: auto;
}

.cart-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #ddd;
}

.cart-item img {
  width: 50px;
  height: 50px;
  object-fit: cover;
  border-radius: 5px;
}

.cart-item-details {
  flex: 1;
  margin-left: 10px;
}

.remove-btn {
  padding: 5px 10px;
  border-radius: 5px;
  border: none;
  background-color: #e74c3c;
  color: white;
  cursor: pointer;
}

.checkout-btn {
  margin-top: 20px;
  padding: 10px;
  border-radius: 5px;
  border: none;
  background-color: #27ae60;
  color: white;
  cursor: pointer;
  width: 100%;
  text-align: center;
}

.no-items {
  text-align: center;
  font-size: 18px;
  color: #666;
  margin-top: 20px;
}
</style>
