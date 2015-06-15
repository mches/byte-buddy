package net.bytebuddy.description.method;

import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.generic.GenericTypeDescription;
import net.bytebuddy.description.type.generic.GenericTypeList;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class MethodDescriptionLatentTest extends AbstractMethodDescriptionTest {

    @Override
    protected MethodDescription describe(Method method) {
        return new MethodDescription.Latent(new TypeDescription.ForLoadedType(method.getDeclaringClass()),
                new MethodDescription.ForLoadedMethod(method).asToken());
    }

    @Override
    protected MethodDescription describe(Constructor<?> constructor) {
        return new MethodDescription.Latent(new TypeDescription.ForLoadedType(constructor.getDeclaringClass()),
                new MethodDescription.ForLoadedConstructor(constructor).asToken());
    }

    @Test
    public void testTypeInitializer() throws Exception {
        TypeDescription typeDescription = mock(TypeDescription.class);
        MethodDescription typeInitializer = MethodDescription.Latent.typeInitializerOf(typeDescription);
        assertThat(typeInitializer.getDeclaringType(), is(typeDescription));
        assertThat(typeInitializer.getReturnType(), is((GenericTypeDescription) new TypeDescription.ForLoadedType(void.class)));
        assertThat(typeInitializer.getParameters(), is((ParameterList) new ParameterList.Empty()));
        assertThat(typeInitializer.getExceptionTypes(), is((GenericTypeList) new GenericTypeList.Empty()));
        assertThat(typeInitializer.getDeclaredAnnotations(), is((AnnotationList) new AnnotationList.Empty()));
        assertThat(typeInitializer.getModifiers(), is(MethodDescription.TYPE_INITIALIZER_MODIFIER));
    }

    @Override
    protected boolean canReadDebugInformation() {
        return false;
    }
}
