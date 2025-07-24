<template>
    <v-tabs v-model="localTab" bg-color="transparent" color="basil" grow>
        <v-tab v-for="item in items" 
            :key="item.value"
            :text="item.label"
            :value="item.value" 
        />
    </v-tabs>
</template>

<script setup lang="ts">
import { ref, watch, defineProps } from 'vue'

const props = defineProps<{ modelValue: string }>()
const emit = defineEmits(['update:modelValue'])

const localTab = ref(props.modelValue)

watch(localTab, (val) => {
  emit('update:modelValue', val)
})

watch(
  () => props.modelValue,
  (val) => {
    if (val !== localTab.value) localTab.value = val
  }
)

const items = [  
    { label: 'Release',     value: 'ReleaseTab' },
    { label: 'Rework',      value: 'ReworkTab' },
    { label: 'Contents1',   value: 'Contents1Tab' },
    { label: 'Contents2',   value: 'Contents2Tab' },
    { label: 'Equip',       value: 'EquipTab' }
]
</script>
