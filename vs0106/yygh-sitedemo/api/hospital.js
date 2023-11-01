import request from '@/utils/request'

export default{
    getHospitalList(searchObj){
        return request({
            url:'/user/hospital/list',
           
            method:'get',
            params:searchObj
        })
    },
    getByHosname(name){
        return request({
            url:`/user/hospital/${name}`,
            method:'get'
        })
    }

}