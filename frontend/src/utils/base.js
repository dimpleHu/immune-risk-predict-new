const base = {
    get() {
        return {
            url : "http://localhost:8085/immuse-risk/",
            name: "immuse-risk",
        };
    },
    getProjectName(){
        return {
            projectName: "自身免疫病风险预测平台"
        }
    }
}
export default base
