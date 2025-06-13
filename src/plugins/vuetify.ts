import '@mdi/font/css/materialdesignicons.css'
import '../styles/_variables.scss'
import { createVuetify, ThemeDefinition } from 'vuetify'
import { aliases, mdi } from 'vuetify/lib/iconsets/mdi'
import 'vuetify/styles'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const dark: ThemeDefinition = {
  dark: true,
  colors: {
    background: '#232429',
    surface: '#4953de',
    'on-surface': '#ffffff',
    primary: '#4953de',
    'on-primary': '#ffffff',
    secondary: '#9da2a6',
    'secondary-darken-1': '#018786',
    error: '#B00020',
    info: '#2196F3',
    success: '#00d452',
    warning: '#FB8C00',
  }
}

export default createVuetify({
   components,
   directives,
  theme: {
    defaultTheme: 'dark',
    themes: {
      dark,
    },
  },
  icons: {
    defaultSet: 'mdi',
    aliases,
    sets: {
      mdi,
    },
  },
})