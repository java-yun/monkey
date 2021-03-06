/**
 * 适配layui框架的table 组件
 * pageConfig 是一个页面配置的json对象，包含三个对象属性：
 var pageConfig = {
        table: {
            render: {
                url: '',
                cols: [[]]
            },
            tools: {}
        },
        form: {
            on: []
        },
        active: {}
 }
 if (honglu.adapter.layui.init) {
        honglu.adapter.layui.init(pageConfig);
 }
 *
 * 1：table：必填项
 *      页面内table必须的配置，包含两个属性对象：
 *      1.1：render： table渲染的配置
 *          {
 *              url：    必填项。页面数据加载的url
 *              cols：   必填项。页面数据展示的列配置
 *              ......其它配置项同layui.table.render相同
 *          }
 *      1.2：tools：table操作列中的动作按钮事件（查看，编辑，删除...）
 *              tools json对象格式为：key：{}
 *              {
 *                  // key: 为定义按钮的html标签lay-event属性值相同。eg：lay-event="view"，则key为：view
 *                  // {}: json值对象包含属性有：
 *                  // {
 *                  //    title：  选填项。弹窗标题，配合url属性组合使用
 *                  //    url：    选填项。弹窗远程页面url，配合title属性组合使用，当有title和url属性时，invoke可以不填
 *                  //    invoke： 选填项。需要执行的function，入参obj为当前行的数据对象，一般单独使用该属性对象（即title，url都不填写）
 *                  // }
 *                  key:{}
 *              }
 *
 * 2：form：选填项
 *      数据的搜索表单配置，包含数组对象on
 *      on:[{
 *          event：      必填项，字符串，表单内绑定事件名称
 *          callback：   必填项，function，响应事件事件，入参data为当前响应事件的对象
 *      }]
 * 3：active：选填项，页面内$('.layui-btn.opt-btn')按钮的点击事件
 *      {
 *          // key为html标签内属性data-type的值，eg：data-type="add"，key为add
 *          // {}：响应事件后的处理函数
 *          key：{}
 *      }
 */
(function (win) {
    let honglu = win.honglu || {};
    honglu = $.extend({
        adapter: {
            layui: {}
        }
    }, honglu);
    win.honglu = honglu;

    // 页面初始化
    honglu.adapter.layui.init = function (pageConfig) {

        // 页面table渲染需要的参数。请配置url，cols属性
        const tableParam = $.extend({
            id: 'dataList',
            elem: '#dataList',
            url: '',
            cols: [[]],
            page: true,
            height: 'full-83'
        }, pageConfig.table.render || {});
        if (!$(tableParam.elem).length) {
            console.error("请正确配置table标签id属性，当前配置：" + tableParam.elem + "不存在");
            return false;
        }
        // 页面table中，操作列中的内容
        // url 参数值使用{{fieldName}},eg: ?id={{id}} or /item/{{id}}
        // 查看，编辑，删除
        const tableTools = $.extend({
            detail: {
                event: "view",
                title: "查看明细",
                url: ""
            },
            edit: {
                event: "edit",
                title: "编辑",
                url: ""
            },
            delete: {
                event: "delete",
                message: "确认删除",
                url: "",
                success: function (result) {
                }
            }
        }, pageConfig.table.tools || []);

        if (!tableParam.url) {
            honglu.dialog.alert("请配置pageConfig.table.render对象中的url属性");
            return false;
        }
        if (!tableParam.cols) {
            honglu.dialog.alert("请配置pageConfig.table.render对象中的cols属性");
            return false;
        }
        layui.use(['form', 'table', 'laydate'], function () {
            const table = layui.table,
                form = layui.form;
            $("input[lay-date]").each(function () {
                const _this = this;
                const conf = $.extend({
                    elem: '#' + _this.id,
                    theme: 'molv',
                    type: 'datetime',
                    isInitValue: !!_this.value
                }, eval('(' + ($(this).attr("lay-date") || '{}') + ')'));
                const format = conf["format"];
                if (format) {
                    if (/^y+[-/]M+[-/]d+$/.test(format)) {
                        // 只做格式为：yyyy-MM-dd的检查，其它格式暂无需求
                        conf["type"] = "date";
                    }
                }
                if (_this.value) {
                    conf["value"] = _this.value;
                }
                layui.laydate.render(conf);
            });

            //方法级渲染
            table.render(tableParam);

            //监听工具条
            table.on('tool(' + tableParam.id + ')', function (obj) {
                const data = obj.data;
                if (tableTools.hasOwnProperty(obj.event)) {
                    const tool = tableTools[obj.event];
                    if (tool && typeof (tool) === "object") {
                        const temp = $.extend({}, tool);
                        if (temp.hasOwnProperty("invoke")) {
                            // 当存在invoke字段时，则直接调用invoke函数
                            temp.invoke(data);
                        } else {
                            for (let k in temp) {
                                temp[k] = replaceVariable(temp[k], data);
                            }
                            // 则为直接弹窗
                            honglu.dialog.open(temp);
                        }
                    }
                } else {
                    console.error("未配置[" + obj.event + "]的动作");
                }
            });

            //监听排序事件
            //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            table.on('sort(' + tableParam.id + ')', function (obj) {
                let where;
                if (tableParam.sort && typeof (tableParam.sort === 'function')) {
                    where = tableParam.sort(obj);
                } else {
                    where = { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                        sort: obj.field, //排序字段
                        direction: obj.type //排序方式
                    };
                }

                table.reload(tableParam.id, {
                    initSort: obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态。
                    where: where
                });
            });

            // 搜索按钮监听
            form.on('submit(search)', function (data) {
                const field = data.field;

                if (field.hasOwnProperty("page")) {
                    field.page = field.page || 1;
                }

                //执行重载
                table.reload(tableParam.id, {
                    where: field
                });
            });
            if (pageConfig.form) {
                const on = pageConfig.form.on || [];
                honglu.forEach(on, function (item) {
                    form.on(item.event, item.callback);
                })
            }

            const active = pageConfig.active || {};

            $('.layui-btn.opt-btn').on('click', function () {
                const type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });

            form.render();
        });
    }

    /**
     * 替换字符串中的变量{{key}}，生成实际的字符串
     * @param str 源字符串
     * @param obj 数据对象
     * @returns {*}
     */
    const replaceVariable = function (str, obj) {
        const keys = getKey(str);
        honglu.forEach(keys, function (item) {
            const reg = new RegExp("{{" + item + "}}", "g");
            str = str.replace(reg, getVal(obj, item));
        })
        return str;
    }

    /**
     * 获取字符串中的变量列表数据
     * @param str 待分析的字符串
     * @returns {Array}
     */
    function getKey(str) {
        if (!str) {
            return [];
        }
        const temp = [];
        const match = str.match(/{{\w+}}/g);
        honglu.forEach(match, function (item) {
            temp.push(item.replace(/\{/g, "").replace(/\}/g, ""));
        })
        return temp;
    }

    /**
     * 获取对象中字段的值
     * @param obj 源对象
     * @param field 字段名称
     * @returns {*}
     */
    function getVal(obj, field) {
        if (obj.hasOwnProperty(field)) {
            return obj[field];
        }
        return "";
    }

})(window);