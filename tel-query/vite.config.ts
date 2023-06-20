import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// https://vitejs.dev/config/
// noinspection JSUnusedGlobalSymbols
export default defineConfig({
	plugins: [vue()],
	build: {
		outDir: "../src/main/resources/static",
	}
})
