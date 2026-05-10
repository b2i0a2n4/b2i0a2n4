<template>
  <div id="app-wrapper">
    <div class="app-overlay"></div>
    <div class="app-content">
      <router-view />
    </div>
  </div>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');

body {
  margin: 0;
  padding: 0;
  font-family: 'Inter', system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
}

#app-wrapper {
  min-height: 100vh;
  width: 100vw;
  /* Premium immersive background image */
  background-image: url('@/assets/background.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  position: relative;
  overflow-x: hidden;
}

.app-overlay {
  position: absolute;
  top: 0; left: 0; right: 0; bottom: 0;
  /* Darken the background globally for readability */
  background: rgba(10, 15, 30, 0.4);
  z-index: 1;
  pointer-events: none;
}

.app-content {
  position: relative;
  z-index: 2;
  min-height: 100vh;
}
</style>