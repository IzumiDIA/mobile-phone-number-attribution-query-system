<script setup lang="ts">
import { ref } from 'vue'
const input = ref('')
</script>

<template>
	<div class="common-layout">
		<form method="get" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8"
		      oninput="queryNumberInfo(this);" id="query-form">
			<el-container>
				<el-header>
					所属运营商：<output class="result" for="phoneNumber" name="operatorName"></output>
				</el-header>
				<el-main>
					<label>
						<input type="tel" maxlength="13" name="phoneNumber" id="phoneNumber" value="138">
					</label>
				</el-main>
				<el-footer class="footer">
					<span>🌏所属地区</span>
					<span>所属省份：<output class="result" for="phoneNumber" name="province"></output></span>
					<span>所属城市：<output class="result" for="phoneNumber" name="city"></output></span>
				</el-footer>
			</el-container>
		</form>
	</div>
</template>

<style scoped>
.common-layout {
	font-size: 1.25em;
	margin-inline: auto;
	padding-inline: .5em;
	padding-block-start: .4em;
	padding-block-end: 1.2em;
	border: #747bff groove .1em;
	border-radius: 1em;
}
.footer {
	display: grid;
	grid-template-areas:    "a b"
							"a c";
}
.footer span:nth-of-type(1) {
	grid-area: a;
	text-align: center;
	place-self: center;
	& output.result {
		font-size: 1.2em;
	}
}
.footer span:nth-of-type(2) {
	grid-area: b;
}
.footer span:nth-of-type(3) {
	grid-area: c;
}
output.result {
	color: #272b90;
	font-weight: bolder;
}
output.result[name="province"]:not(:empty)::after {
	content: '省';
	margin-inline-start: .1em;
}
output.result[name="city"]:not(:empty)::after {
	content: '市';
}
#phoneNumber {
	font-size: 1.5em;
	font-weight: bolder;
	max-width: 10em;
	text-align: center;
	padding-inline: .5em;
	border-radius: .6em;
	&:focus {
		box-shadow: 0 0 .3em .02em #019601;
	}
}
</style>
