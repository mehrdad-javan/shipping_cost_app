package se.lexicon.shipping_cost.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler({ArgumentException.class})
    public ModelAndView argumentException(ArgumentException ex) {
        MyError error = new MyError(ex.getMessage(), ex);
        return new ModelAndView("customError", "error", error);
    }

    @ExceptionHandler({RecordDuplicateException.class})
    public ModelAndView recordDuplicateException(RecordDuplicateException ex) {
        MyError error = new MyError(ex.getMessage(), ex);
        return new ModelAndView("customError", "error", error);
    }

    @ExceptionHandler({RecordNotFountException.class})
    public ModelAndView recordNotFountException(RecordNotFountException ex) {
        MyError error = new MyError(ex.getMessage(), ex);
        return new ModelAndView("customError", "error", error);
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView exception(Exception ex) {
        System.out.println("INTERNAL ERROR");
        ex.printStackTrace();
        return new ModelAndView("error", "error", "INTERNAL ERROR");
    }

}
