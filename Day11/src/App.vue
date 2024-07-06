<template>
    <router-view></router-view>
  </template>
  
  <script>
  import { api } from './config';
  import API from '@/api/test-api.js';
  export default {
    name: 'App',
    created(){
      this.getAllProducts()
    },
    methods :{
    getAllProducts(){
     API.getDataByAPI(api.getAllProducts)
     .then(response=>{
      console.log('res',response)
     })
    }
  }
  };
 
  </script>
  
  <style scoped>
body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  margin: 0;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #f4f4f4;
}
  </style>
  