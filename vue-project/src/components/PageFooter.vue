<template>
  <div id="background">
    <el-image
        style="width: 100%;height: 120px"
        :src="image"
        :fit="cover"></el-image>
  </div>
</template>

<script>

export default {
  data(){
    return{
      image:require("@/assets/Footer.png"),
    }
  },
  methods:{
  }
}
</script>

<style scoped>
.background{
  height: 100%;
}
</style>