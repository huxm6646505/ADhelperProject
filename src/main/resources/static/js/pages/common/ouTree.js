/**
 * Created by maozz11347 on 2017/8/28.
 */
/**
 * 请求地址
 *
 * @param addressUrl
 * @returns
 */
function loadTreeNode(addressUrl, treeComponentId) {
    // 组织树参数
    var setting = {
        async: {
            enable: true,
            url: addressUrl,
            autoParam: ["id"]
        },
        check: {
            enable: true
        },
        data: {
            key: {
                name: "name"
            },
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",

            },
            view: {
                selectedMulti: true
            }
        }
    };

    $.get(addressUrl, function (result) {
        if (result) {
            $.fn.zTree.init($("#" + treeComponentId), setting, result.item);
            var treeObj = $.fn.zTree.getZTreeObj(treeComponentId);
         /*   var nodes = treeObj.getNodes();
            for (var i = 0; i < nodes.length; i++) { //设置节点展开
                treeObj.expandNode(nodes[i], true, true, true);
            }*/
            treeObj.expandNode(treeObj.getNodeByParam("id","OU=大族控股集团有限公司,OU=大族集团,DC=hansholdings,DC=com",null));//展开指定节点
        }
    });
}