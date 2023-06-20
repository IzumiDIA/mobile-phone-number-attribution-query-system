"use strict";

let prefixCache = undefined, operatorNameCache;

/**
 * @param formElement {!HTMLFormElement}
 */
function queryNumberInfo(formElement) {
	const number = formElement.phoneNumber.value.replaceAll(' ', '');
	if ( number.length > 2 ) {
		formElement.phoneNumber.value = telFormat(number);
		if ( number.length === 11 ) {
			queryDetailNumberInfo(formElement, number);
		}else if ( number.length < 11 ) {
			formElement.province.value = '';
			formElement.city.value = '';
			if ( number.startsWith(prefixCache) ) {
				formElement.operatorName.value = operatorNameCache;
			}else {
				prefixCache = number.substring(0, 3);
				queryOperatorName(formElement);
			}
		}
	}else {
		formElement.operatorName.value = '';
	}
}

/**
 * @param number {!string}
 * @return {string}
 */

function telFormat(number) {
	if ( number.length > 7 ) {
		return `${number.substring(0, 3)} ${number.substring(3, 7)} ${number.substring(7)}`;
	}else if ( number.length > 3 ) {
		return `${number.substring(0, 3)} ${number.substring(3)}`;
	}else return number;
}

/**
 * @param formElement {!HTMLFormElement}
 * @param networkIdentificationNumber {?string}
 */
function queryOperatorName(formElement, networkIdentificationNumber = prefixCache) {
	window.fetch(`./query/belongingToTheOperator?netIDNum=${networkIdentificationNumber}`, {
		method: "GET",
		mode: "no-cors",
		credentials: "omit",
		cache: "force-cache",
		redirect: "error"
	}).then(response => {
		if ( response.ok ) {
			return response?.text();
		}else {
			throw new Error(response?.text());
		}
	}).then(data => formElement.operatorName.value = operatorNameCache = data)
	      .catch(e => {
		      e.cause.then(text => {
			      window.alert(`发生错误：${e.message}: ${text}`);
		      })
	      });
}

/**
 * @param formElement {!HTMLFormElement}
 * @param number {!string}
 */
function queryDetailNumberInfo(formElement, number) {
	window.fetch(`./query/detailNumberInfo?telNum=${number}`, {
		method: "GET",
		mode: "no-cors",
		credentials: "omit",
		redirect: "error"
	}).then(response => {
		if ( response.ok ) {
			return response?.json();
		}else {
			throw new Error(response?.text());
		}
	}).then(queryResult => {
		const { city, province, sp } = queryResult["data"];
		prefixCache = number.substring(0, 3);
		operatorNameCache = sp;
		formElement.operatorName.value = operatorNameCache;
		formElement.province.value = province;
		formElement.city.value = city;
	}).catch(e => {
		e.cause.then(text => {
			window.alert(`发生错误：${e.message}: ${text}`);
		})
	});
}

window.document["queryNumberInfo"] = queryNumberInfo;

export {
	queryNumberInfo
}
