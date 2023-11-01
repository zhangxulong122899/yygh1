var add = function(a,b){
    return parseInt(a) + parseInt(b)
}
var substract = function(a,b){ 
    return parseInt(a) - parseInt(b)
}
var multiply = function(a,b){
    return parseInt(a) * parseInt(b)
}
var divide = function(a,b){
    return parseInt(a) / parseInt(b)
}
// 在js文件中定义的这些方法相当于java中由private修饰的方法，
// 在外界是不能狗直接调用的，外界想要调用 需要导出一下
module.exports={
    add:add,
    substract:substract,
    multiply:multiply,
    divide:divide
}
