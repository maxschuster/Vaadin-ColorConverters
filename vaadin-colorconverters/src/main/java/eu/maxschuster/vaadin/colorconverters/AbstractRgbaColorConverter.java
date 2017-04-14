/*
 * Copyright 2017 Max Schuster.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.maxschuster.vaadin.colorconverters;

import com.vaadin.data.Converter;
import com.vaadin.shared.ui.colorpicker.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base class for {@link Converter}s that convert between CSS rgba() color and {@link Color}
 * 
 * @author Max Schuster
 * @param <PRESENTATION> The presentation type.
 * @param <MODEL> The model type.
 */
public abstract class AbstractRgbaColorConverter<PRESENTATION, MODEL>
        extends AbstractRgbColorConverter<PRESENTATION, MODEL>
        implements ColorConverter<PRESENTATION, MODEL> {
    
    private static final long serialVersionUID = 1L;

    private static final Pattern RGBA_PATTERN = Pattern.compile(
            "^rgba\\(\\s*(\\d{0,3})\\s*,\\s*(\\d{0,3})\\s*,\\s*(\\d{0,3})\\s*,\\s*([01](\\.\\d+)?)\\s*\\)$",
            Pattern.CASE_INSENSITIVE);

    public AbstractRgbaColorConverter(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String serializeColor(Color color) throws ColorConverterException {
        double alpha = intToDouble(color.getAlpha());
        String alphaString;
        if (alpha == (long) alpha) {
            alphaString = String.format("%d", (long) alpha);
        } else {
            alphaString = String.format("%s", alpha);
        }
        return String.format("rgba(%d,%d,%d,%s)", color.getRed(),
                color.getGreen(), color.getBlue(), alphaString);
    }

    @Override
    public Color unserializeColor(String string) throws ColorConverterException {
        Matcher m = RGBA_PATTERN.matcher(string);
        if (!m.matches()) {
            throw new ColorConverterException("Could not convert '" + string
                    + "' to a css rgba color");
        }
        int red = parseColor(m.group(1));
        int greed = parseColor(m.group(2));
        int blue = parseColor(m.group(3));
        int alpha = parseAlpha(m.group(4));
        return new Color(red, greed, blue, alpha);
    }

    protected int parseAlpha(String colorString) throws ColorConverterException {
        if (colorString == null) {
            throw new ColorConverterException("Color string mustn't be null");
        }
        double alpha;
        try {
            alpha = Double.valueOf(colorString);
        } catch (NumberFormatException e) {
            throw new ColorConverterException("Can't convert color string '"
                    + colorString + "' to double", e);
        }
        if (alpha < 0 || alpha > 1) {
            throw new ColorConverterException("Illegal value of color '" + alpha + "'");
        }
        return doubleToInt(alpha);
    }

    protected double intToDouble(int alpha) {
        return alpha / 255d;
    }

    protected int doubleToInt(double alpha) {
        return (int) Math.round(alpha * 255d);
    }
    
}
