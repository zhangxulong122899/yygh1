import request from '@/utils/request'

export default{
    getChildList(pid){
        return request({
            url:`/admin/cmn/childList/${pid}`,
            method:'get'
        })
    }
}