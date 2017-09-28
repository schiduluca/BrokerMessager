package broker.dto;

public class CustomMessage implements Message {

    private static final long serialVersionUID = -3706310678806165793L;

    private String name;
    private Integer age;

    public CustomMessage(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String getData() {
        return this.name;
    }

    @Override
    public Class getClassType() {
        return CustomMessage.class;
    }

}
