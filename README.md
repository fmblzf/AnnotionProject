# AnnotionProject
验证自定义注解类的操作过程：
1.创建对应的注解类；
2.创建对应的注解解析工具类；
## 创建注解类需要注意的地方：
+ 使用@interface关键字；
+ @Documented表示在javadoc工具处理，生成对应的api文档时，是否显示方法中使用的注解信息，如果不使用该标注就不会显示；
+ @Target(value={ElementType.TYPE}) 根据使用场景进行配置
+ @Inherited 表示如果父类使用了该注解，但是子类没有使用，在解析注解时，会追溯到使用了该注解的父类，直到找到为止；
+ @Retention(value=RetentionPolicy.RUNTIME) 配置注解保留的时间点：
- 1.RetentionPolicy.SOURCE —— 这种类型的Annotations只在源代码级别保留,编译时就会被忽略
- 2.RetentionPolicy.CLASS —— 这种类型的Annotations编译时被保留,在class文件中存在,但JVM将会忽略
- 3.RetentionPolicy.RUNTIME —— 这种类型的Annotations将被JVM保留,所以他们能在运行时被JVM或其他使用反射机
## 创建注解解析工具
使用反编译技术，获取注解信息以及将注解需要实现的信息嵌入到属性（可能是赋值嵌入），方法（可能是事件方法配置的嵌入），类（整个类的内置元素的嵌入）；

## 利用JAVAC实现编译期注解生成
+ 生成CreateInterfaceProcessor.class 和 GenerInteface.class
>javac -d classes\ src\test\andann\compile\proc\*.java src\test\andann\compile\*.java  -encoding UTF-8

+ 执行命令生成对应的java源文件
>javac -cp classes\ -processor test.andann.compile.proc.CreateInterfaceProcessor -d classes\ -s src\ src\test\andann\compile\bean\*.java -encoding UTF-8

## 总结：
通过注解+反编译技术，我们就可以在设计框架或者熟悉现有框架都可以做到很好的设计和理解。现有好多框架都使用了注解+反编译来实现配置信息的预置，或者方法中不可估量（或者说需要具体场景来确定的东西），都可以使用注解的方式来实现动态配置处理，还有许多共性的东西，比如一类东西可以有一个统一的处理方式，但是这个统一的方式又需要其他外在元素来决定时，可以使用之。


