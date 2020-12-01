package ktsnwt_tim8.demo.helper;

public interface MapperInterface<T,U> {

	T toEntity(U dto);

    U toDto(T entity);
}
