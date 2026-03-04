<template>
    <div class="not-found">
      <h1>Oops! Page not found</h1>
      <p>The page you're looking for doesn't exist or an error occurred.</p>
      <router-link to="/">Go back home</router-link>
    </div>
  </template>
  
  <script>
  export default {
    name: 'NotFound',
  };
  </script>
  
  <style scoped>
  .not-found {
    text-align: center;
    margin-top: 50px;
  }
  
  .not-found h1 {
    font-size: 2em;
    color: #e74c3c; /* A shade of red */
  }
  
  .not-found p {
    font-size: 1.2em;
    margin-bottom: 20px;
  }
  
  .not-found a {
    text-decoration: none;
    background-color: #3498db; /* A shade of blue */
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    transition: background-color 0.3s ease;
  }
  
  .not-found a:hover {
    background-color: darken(#3498db, 10%); /* Slightly darker on hover */
  }
  </style>