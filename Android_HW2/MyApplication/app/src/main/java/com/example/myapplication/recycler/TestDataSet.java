package com.example.myapplication.recycler;

import java.util.ArrayList;
import java.util.List;

public class TestDataSet {

    public static List<TestData> getData() {
        List<TestData> result = new ArrayList();
        result.add(new TestData("七里香", "1032.7w"));
        result.add(new TestData("可惜没如果", "833.4w"));
        result.add(new TestData("明明就", "758.9w"));
        result.add(new TestData("稻香", "543.6w"));
        result.add(new TestData("好久不见", "413.3w"));
        result.add(new TestData("单车", "324.1w"));
        result.add(new TestData("老街", "307.9w"));
        result.add(new TestData("北京北京", "254.3w"));
        result.add(new TestData("后来", "185.3w"));
        result.add(new TestData("像我这样的人","96w"));
        result.add(new TestData("爱就一个字", "72.5w"));
        result.add(new TestData("乌鸦","23.2w"));
        return result;
    }

}
