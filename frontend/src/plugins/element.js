import Vue from 'vue'
import {
    Avatar,
    Backtop,
    Badge,
    Breadcrumb,
    BreadcrumbItem,
    Button,
    Card,
    Carousel,
    CarouselItem,
    Checkbox,
    CheckboxGroup,
    Col,
    Container,
    DatePicker,
    Dialog,
    Divider,
    Drawer,
    Dropdown,
    DropdownItem,
    DropdownMenu,
    Empty,
    Footer,
    Form,
    FormItem,
    Header,
    Image,
    Input,
    InputNumber,
    Loading,
    Main,
    Menu,
    MenuItem,
    MenuItemGroup,
    Message,
    MessageBox,
    Notification,
    Option,
    Pagination,
    Progress,
    Radio,
    RadioGroup,
    Rate,
    Row,
    Select,
    Step,
    Steps,
    Submenu,
    Table,
    TableColumn,
    TabPane,
    Tabs,
    Tag,
    Timeline,
    TimelineItem,
    TimePicker,
    Tooltip,
    Upload
} from 'element-ui'

// 注册组件
Vue.use(Button)
Vue.use(Menu)
Vue.use(Submenu)
Vue.use(MenuItem)
Vue.use(MenuItemGroup)
Vue.use(Row)
Vue.use(Col)
Vue.use(Card)
Vue.use(Input)
Vue.use(InputNumber)
Vue.use(Select)
Vue.use(Option)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Pagination)
Vue.use(Dialog)
Vue.use(Dropdown)
Vue.use(DropdownMenu)
Vue.use(DropdownItem)
Vue.use(Tabs)
Vue.use(TabPane)
Vue.use(Tag)
Vue.use(Tooltip)
Vue.use(Breadcrumb)
Vue.use(BreadcrumbItem)
Vue.use(DatePicker)
Vue.use(TimePicker)
Vue.use(Checkbox)
Vue.use(CheckboxGroup)
Vue.use(Radio)
Vue.use(RadioGroup)
Vue.use(Upload)
Vue.use(Badge)
Vue.use(Progress)
Vue.use(Timeline)
Vue.use(TimelineItem)
Vue.use(Divider)
Vue.use(Rate)
Vue.use(Steps)
Vue.use(Step)
Vue.use(Header)
Vue.use(Footer)
Vue.use(Main)
Vue.use(Carousel)
Vue.use(CarouselItem)
Vue.use(Drawer)
Vue.use(Backtop)
Vue.use(Image)
Vue.use(Avatar)
Vue.use(Container)
Vue.use(Empty)

// 注册指令
Vue.use(Loading.directive)

// 挂载到Vue原型链上
Vue.prototype.$loading = Loading.service
Vue.prototype.$msgbox = MessageBox
Vue.prototype.$alert = MessageBox.alert
Vue.prototype.$confirm = MessageBox.confirm
Vue.prototype.$prompt = MessageBox.prompt
Vue.prototype.$notify = Notification
Vue.prototype.$message = Message 