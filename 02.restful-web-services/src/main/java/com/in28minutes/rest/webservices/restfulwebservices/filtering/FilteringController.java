package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public SomeBean filtering(){
        return new SomeBean("value1","value2","value3");
    }

    @GetMapping("/filtering-list")
    public List<SomeBean> filteringList(){
        return Arrays.asList(new SomeBean("value1","value2","value3")
                ,new SomeBean("value4","value5","value6"));
    }

    @GetMapping("/filtering-with-view") //field1 and field3
    @JsonView(View.View1.class)
    public SomeBean filteringWithView(){
        return new SomeBean("value1","value2","value3");
    }

    @GetMapping("/filtering-list-with-view") //field2 and field3
    @JsonView(View.View2.class)
    public List<SomeBean> filteringListWithView(){
        return Arrays.asList(new SomeBean("value1","value2","value3")
                ,new SomeBean("value4","value5","value6"));
    }

}