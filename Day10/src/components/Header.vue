<template>
  <header class="header">
    <h2><b>Blibli</b></h2>
    <input type="search" :value="searchQuery" @input="$emit('update:searchQuery', $event.target.value)" placeholder="Search...">
  </header>
  <div class="filters">
      <label for="minPrice">Min Price:</label>
      <input type="number" id="minPrice" v-model="minPrice" @input="applyFilters" placeholder="Min Price">
      <label for="maxPrice">Max Price:</label>
      <input type="number" id="maxPrice" v-model="maxPrice" @input="applyFilters" placeholder="Max Price">
      <label for="availableStock">Available Stock:</label>
      <input type="checkbox" id="availableStock" v-model="availableStock" @change="applyFilters">
    </div>
</template>

<script>
export default {
  props: {
    searchQuery: String,
  },
  data() {
    return {
      minPrice: null,
      maxPrice: null,
      availableStock: false,
    };
  },
  methods: {
    applyFilters() {
      this.$emit('update:filters', {
        minPrice: this.minPrice,
        maxPrice: this.maxPrice,
        availableStock: this.availableStock,
      });
    },
  },
};
</script>

<style scoped>
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

.filters {
  display: flex;
  align-items:center;
  background-color: #f0f0f0;
  padding: 10px 40px;
  margin-bottom: 10px;
}

.filters label {
  margin-right: 10px;
}
</style>
