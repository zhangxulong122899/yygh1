/*
 * ATTENTION: The "eval" devtool has been used (maybe by default in mode: "development").
 * This devtool is neither made for production nor for readable output files.
 * It uses "eval()" calls to create a separate source file in the browser devtools.
 * If you are trying to read the output file, select a different devtool (https://webpack.js.org/configuration/devtool/)
 * or disable the default devtool with "devtool: false".
 * If you are looking for production-ready output files, see mode: "production" (https://webpack.js.org/configuration/mode/).
 */
/******/ (() => { // webpackBootstrap
/******/ 	var __webpack_modules__ = ({

/***/ "./src/01.js":
/*!*******************!*\
  !*** ./src/01.js ***!
  \*******************/
/***/ ((module) => {

eval("\r\nfunction write(str){\r\n    document.write(str);\r\n}\r\n\r\nmodule.exports={\r\n    write:write\r\n}\n\n//# sourceURL=webpack://webpack01/./src/01.js?");

/***/ }),

/***/ "./src/02.js":
/*!*******************!*\
  !*** ./src/02.js ***!
  \*******************/
/***/ ((module) => {

eval("var add = function(a,b){\r\n   return parseInt(a) + parseInt(b)\r\n}\r\n\r\nmodule.exports = {\r\n    add:add\r\n}\n\n//# sourceURL=webpack://webpack01/./src/02.js?");

/***/ }),

/***/ "./src/main.js":
/*!*********************!*\
  !*** ./src/main.js ***!
  \*********************/
/***/ ((__unused_webpack_module, __unused_webpack_exports, __webpack_require__) => {

eval("var obj1 = __webpack_require__(/*! ./01 */ \"./src/01.js\")\r\nvar obj2 = __webpack_require__(/*! ./02 */ \"./src/02.js\")\r\nobj1.write(obj2.add(1,4))\r\nconsole.log('hello webpack ' + obj2.add(1,4))\r\n\r\n\r\n\n\n//# sourceURL=webpack://webpack01/./src/main.js?");

/***/ })

/******/ 	});
/************************************************************************/
/******/ 	// The module cache
/******/ 	var __webpack_module_cache__ = {};
/******/ 	
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/ 		// Check if module is in cache
/******/ 		var cachedModule = __webpack_module_cache__[moduleId];
/******/ 		if (cachedModule !== undefined) {
/******/ 			return cachedModule.exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = __webpack_module_cache__[moduleId] = {
/******/ 			// no module.id needed
/******/ 			// no module.loaded needed
/******/ 			exports: {}
/******/ 		};
/******/ 	
/******/ 		// Execute the module function
/******/ 		__webpack_modules__[moduleId](module, module.exports, __webpack_require__);
/******/ 	
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/ 	
/************************************************************************/
/******/ 	
/******/ 	// startup
/******/ 	// Load entry module and return exports
/******/ 	// This entry module can't be inlined because the eval devtool is used.
/******/ 	var __webpack_exports__ = __webpack_require__("./src/main.js");
/******/ 	
/******/ })()
;