<template>
  <div class="backround">
    <el-container>
      <el-header>
        <page-header></page-header>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <page-aside></page-aside>
        </el-aside>
        <el-container>
          <el-main>
            <div>
              <component :is="comName"></component>
            </div>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import GoodsList from '@/components/GoodsList.vue'
import OrderList from '@/components/OrderList.vue'
import SaleList from '@/components/SaleList.vue'
import PageAside from "@/components/PageAside.vue";
import PageHeader from "@/components/PageHeader.vue";

export default {
  components:{
    PageAside,
    GoodsList,
    OrderList,
    SaleList,
    PageHeader
  },
  data(){
    return{
      image:require("@/assets/Footer.png"),
      comName:"GoodsList"
    }
  },
  methods:{
    changepage(page){
      this.comName=page
    }
  }
}
</script>

<style scoped>
.el-header{
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
  padding: 0;
}

.el-footer{
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 0;
  padding: 0;
  margin: 0;
}

.el-aside {
  background-color: #545c64;
  color: #333;
  text-align: center;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  min-height: 300px;
}
</style>