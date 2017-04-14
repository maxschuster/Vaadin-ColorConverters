package eu.maxschuster.vaadin.colorconverters.demo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import eu.maxschuster.vaadin.colorconverters.ColorToRgbaConverter;
import eu.maxschuster.vaadin.colorconverters.HexToColorConverter;
import eu.maxschuster.vaadin.colorconverters.RgbToColorConverter;
import eu.maxschuster.vaadin.colorconverters.RgbaToColorConverter;
import java.io.Serializable;

@Theme("valo")
@Title("ColorConverters – Demo")
@Viewport("width=device-width, initial-scale=1.0, user-scalable=no")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }
    
    private class TestDTO implements Serializable {
        
        private String color;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
        
    }
    
    private final DemoUILayout l = new DemoUILayout();

    @Override
    protected void init(VaadinRequest request) {
        setContent(l);
        
        TestDTO bean = new TestDTO();
        
        Binder<TestDTO> binder = new Binder<>(TestDTO.class);
        binder.setBean(bean);
        
        binder.forField(l.dtoValue)
                .withNullRepresentation("null")
                .bind(TestDTO::getColor, TestDTO::setColor);
        
        binder.forField(l.colorPicker)
                .withNullRepresentation(Color.WHITE)
                .withConverter(new ColorToRgbaConverter("Could not convert the string to a color!"))
                .bind(TestDTO::getColor, TestDTO::setColor);
        
        binder.forField(l.rgb)
                .withNullRepresentation("")
                .withConverter(new RgbToColorConverter("Please enter the value in rgb notation!"))
                .withConverter(new ColorToRgbaConverter("Could not convert the string to a color!"))
                .bind(TestDTO::getColor, TestDTO::setColor);
        
        binder.forField(l.rgba)
                .withNullRepresentation("")
                .withConverter(new RgbaToColorConverter("Please enter the value in rgba notation!"))
                .withConverter(new ColorToRgbaConverter("Could not convert the string to a color!"))
                .bind(TestDTO::getColor, TestDTO::setColor);
        
        binder.forField(l.hex)
                .withNullRepresentation("")
                .withConverter(new HexToColorConverter("Please enter the value in hex notation!"))
                .withConverter(new ColorToRgbaConverter("Could not convert the string to a color!"))
                .bind(TestDTO::getColor, TestDTO::setColor);
        
        binder.addValueChangeListener(e -> {
            if (binder.isValid()) {
                binder.readBean(binder.getBean());
            }
        });
        
        l.clear.addClickListener(e -> {
            binder.readBean(new TestDTO());
            Notification.show("Color selection cleared", Notification.Type.TRAY_NOTIFICATION);
        });
        
        l.validate.addClickListener(e -> {
            BinderValidationStatus<TestDTO> status = binder.validate();
            if (status.isOk()) {
                Notification.show("Everything is ok!");
            } else {
                Notification.show("Uh-oh, there is a problem!", Notification.Type.WARNING_MESSAGE);
            }
        });
        
    }
}
