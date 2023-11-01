var obj1 = require('./01')
var obj2 = require('./02')
require('./style.css')
obj1.write(obj2.add(1,4))
console.log('hello webpack ' + obj2.add(1,4))


