package it.simonegiampietro.quarkusproxy.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.enterprise.context.ApplicationScoped;

import io.protostuff.CodedInput;
import io.protostuff.GraphCodedInput;
import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

@ApplicationScoped
public class DaoProtostuff {
    
    public <T> T load(String absoluteFilePath, Class<T> t) throws IllegalArgumentException {
        try (FileInputStream fis = new FileInputStream(absoluteFilePath)) {
            return load(fis, t);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> T load(InputStream inputStream, Class<T> t) throws IllegalArgumentException {
        try {
            Schema<T> schema = RuntimeSchema.getSchema(t);
            T bean = schema.newMessage();
            CodedInput input = new CodedInput(inputStream, true);
            input.setSizeLimit(1024 << 20);
            GraphCodedInput graphInput = new GraphCodedInput(input);
            schema.mergeFrom(graphInput, bean);
            input.checkLastTagWas(0);
            return bean;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public <T> void save(String absoluteFilePath, T bean, Class<T> t) throws IllegalArgumentException {
        try (FileOutputStream fout = new FileOutputStream(absoluteFilePath)) {
            save(bean, fout, t);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> void save(T bean, OutputStream out, Class<T> t) throws IllegalArgumentException {
        if (bean == null) {
            throw new IllegalArgumentException("Unable to save a null object.");
        }
        Schema<T> schema = RuntimeSchema.getSchema(t);
        LinkedBuffer buffer = LinkedBuffer.allocate();
        try {
            GraphIOUtil.writeTo(out, bean, schema, buffer);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        } finally {
            buffer.clear();
        }
    }

}
