<template>
  <div class="ValidCode disabled-select"
       :style="`width:${width}; height:${height}`"
       @click="refreshCode">
    <span v-for="(item, index) in codeList"
          :key="index"
          :style="getStyle(item)">
      {{item.code}}
    </span>
  </div>
</template>

<script setup>
import { ref, onMounted, defineEmits } from 'vue';
const width = '150px';
const height = '40px';
const length = 4;

const codeList = ref([]);
const emit = defineEmits(['input'])


const createdCode = () => {
  const chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz0123456789';
  const charsLen = chars.length;
  const newCodeList = [];

  for (let i = 0; i < length; i++) {
    const rgb = [Math.round(Math.random() * 220), Math.round(Math.random() * 240), Math.round(Math.random() * 200)];
    newCodeList.push({
      code: chars.charAt(Math.floor(Math.random() * charsLen)),
      color: `rgb(${rgb})`,
      fontSize: `1${[Math.floor(Math.random() * 10)]}px`,
      padding: `${[Math.floor(Math.random() * 10)]}px`,
      transform: `rotate(${Math.floor(Math.random() * 90) - Math.floor(Math.random() * 90)}deg)`
    });
  }

  codeList.value = newCodeList;
  emit('input', newCodeList.map(item => item.code).join(''));
};

onMounted(createdCode);

const getStyle = (data) => {
  return `color: ${data.color}; font-size: ${data.fontSize}; padding: ${data.padding}; transform: ${data.transform}`;
};

const refreshCode = () => {
  createdCode();
};
</script>

<style scoped>
.ValidCode{
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  span{
    display: inline-block;
  }
}
</style>