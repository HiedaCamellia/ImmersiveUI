package org.hiedacamellia.immersiveui.client.graphic.element;

import org.hiedacamellia.immersiveui.client.graphic.util.Vec4;

@SuppressWarnings("unused")
public class LayoutElements {

    public static class Margin extends Vec4 {

        public Margin(int left, int top, int right, int bottom) {
            super(left, top, right, bottom);
        }

        public Margin(int value) {
            super(value);
        }

        public Margin() {
            super(0, 0, 0, 0);
        }

    }

    public static class Padding extends Vec4 {

        public Padding(int left, int top, int right, int bottom) {
            super(left, top, right, bottom);
        }

        public Padding(int value) {
            super(value);
        }

        public Padding() {
            super(0, 0, 0, 0);
        }

    }

    public static class Border extends Vec4 {

        public Border(int left, int top, int right, int bottom) {
            super(left, top, right, bottom);
        }

        public Border(int value) {
            super(value);
        }

        public Border() {
            super(0, 0, 0, 0);
        }

    }

    //显示行为
    public enum Display {
        BLOCK, //块级元素 从新行开始并占据可用的全部宽度
        INLINE,//内联元素 不会从新行开始，只占据必要的宽度
        INLINE_BLOCK,//结合了块级和内联元素的特性，允许元素在内联流中，但在宽度和高度方面表现得像块级元素
        FLEX,//弹性盒布局，这是一种用于在容器中排列元素的现代布局模型
        GRID,//网格布局，允许使用二维网格布局系统
        TABLE,//表示表格元素，用于显示表格数据
        TABLE_ROW,//表示表格中的一行
        TABLE_CELL,//表示表格行中的一个单元格
        NONE//表示无显示，实际上隐藏了元素
    }

    //对齐行为
    public enum Align {
        LEFT, //左对齐
        CENTER,//居中对齐
        RIGHT,//右对齐
        JUSTIFY,//两端对齐
        SPACE_BETWEEN,//两端对齐，项目之间的间隔都相等
        SPACE_AROUND,//每个项目两侧的间隔相等，项目之间的间隔也相等
        SPACE_EVENLY//每个项目之间的间隔相等
    }

    //鼠标行为
    public enum Cursor {
        AUTO,//根据元素内容自动判断鼠标行为
        CAPTURE,//捕获鼠标输入
        PENETRATE,//穿透鼠标输入
    }


}
