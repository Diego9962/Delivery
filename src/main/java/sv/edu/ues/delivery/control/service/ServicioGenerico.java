package sv.edu.ues.delivery.control.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import sv.edu.ues.delivery.control.dao.DaoGenerico;

@RequestScoped
public abstract class ServicioGenerico<T, PK> {
    
    @Inject Validator validador;

    protected abstract DaoGenerico<T, PK> obtenerDao();

    public List<T> listar(){
        return obtenerDao().listar();
    }

    public Optional<T> obtenerPorId(PK id){
        return obtenerDao().obtenerPorId(id);
    }

    public T insertar(T entidad){
        
        Set<ConstraintViolation<T>> errores = validador.validate(entidad);
        if(!errores.isEmpty()){
            throw new ConstraintViolationException(errores);
        }
        return obtenerDao().insertar(entidad);
    }

    public T actualizar(T entidad){
        Set<ConstraintViolation<T>> errores = validador.validate(entidad);
        if(!errores.isEmpty()){
            throw new ConstraintViolationException(errores);
        }
        return obtenerDao().actualizar(entidad);
    }

    public T eliminar(T entidad){
        Set<ConstraintViolation<T>> errores = validador.validate(entidad);
        if(!errores.isEmpty()){
            throw new ConstraintViolationException(errores);
        }
        return obtenerDao().eliminar(entidad);
    }
}
