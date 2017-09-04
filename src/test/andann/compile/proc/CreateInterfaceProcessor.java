package test.andann.compile.proc;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import test.andann.compile.GenerInteface;

@SupportedAnnotationTypes("test.andann.compile.GenerInteface")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CreateInterfaceProcessor extends AbstractProcessor {

	
	private Filer filer;
	private Messager messager;
	
	private int r = 1;
	
	private static final String generPkgName = "test.andann.compile.bean";
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		// TODO Auto-generated method stub
		super.init(processingEnv);
		filer = processingEnv.getFiler();
		messager = processingEnv.getMessager();
	}
	
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		// TODO Auto-generated method stub
		messager.printMessage(Kind.NOTE, "process() is execute ... ");
		Set<? extends Element> elements = roundEnv.getRootElements();
		System.out.println("输入的所有类有：");
		for (Element element : elements) {
			System.out.println(">>>"+element.getSimpleName());
		}
		
		//获取使用了@GenerInterface注解的类
		Set<? extends Element> generElements = roundEnv.getElementsAnnotatedWith(GenerInteface.class);
		System.out.println("需要生成接口的类有：");
		for (Element element : generElements) {
			System.out.println(">>>"+element.getSimpleName());
			GenerInteface gi = element.getAnnotation(GenerInteface.class);
			String className = element.getSimpleName()+gi.suffix();
			String classString = "package " + generPkgName+";\n"
					+"public interface "+className+" {\n";
			//获取所有的方法元素
			List<? extends Element> getElementsAll = element.getEnclosedElements();
			System.out.println(">>>类"+element.getSimpleName()+"封装元素（仅对含有修饰符public的生成对应的接口方法）：");
			for (Element e : getElementsAll) {
				System.out.println(">>>"+e.getSimpleName()+"的修饰符："+e.getModifiers());
				if(!e.getSimpleName().toString().equals("<init>")
						&& e.asType() instanceof ExecutableType 
						&& isPublic(e)){
					System.out.println(">>> >>> >>>"+e.getSimpleName());
					classString += " void "+e.getSimpleName()+"();\n";
				}
			}
			classString += "}";
			System.out.println("classString="+classString);
			try {
				JavaFileObject jfo = filer.createSourceFile(generPkgName+"."+className, element);
				Writer writer = jfo.openWriter();
				writer.flush();
				writer.append(classString);
				writer.flush();
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		System.out.println("------------注解处理器第"+(r++)+"次循环处理结束...\n");
		 
		return true;
	}


	private boolean isPublic(Element e) {
		// TODO Auto-generated method stub
		Set<Modifier> modifiers = e.getModifiers();
		for (Modifier modifier : modifiers) {
			if(modifier.equals(Modifier.PUBLIC))
				return true;
		}
		return false;
	}

}
