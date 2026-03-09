package upv.avanzada.graficacion.lesson_eight;

interface ErrorHandler {
	enum ErrorType {
		BUFFER_CREATION_ERROR
	}
	
	void handleError(ErrorType errorType, String cause);
}