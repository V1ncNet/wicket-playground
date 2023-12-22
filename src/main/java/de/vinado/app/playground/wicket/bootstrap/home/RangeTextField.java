package de.vinado.app.playground.wicket.bootstrap.home;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.tempusdominus.TempusDominusBehavior;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.tempusdominus.TempusDominusConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.tempusdominus.TempusDominusLocalizationConfig.DateFormatType;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.convert.converter.AbstractJavaTimeConverter;
import org.apache.wicket.util.string.Strings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RangeTextField extends TextField<Range> {

    private static final String SEPARATOR = " – ";

    private final TempusDominusConfig config;

    public RangeTextField(String id, IModel<Range> model, TempusDominusConfig config) {
        super(id, model, Range.class);

        this.config = config;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Locale locale = Session.get().getLocale();
        config
            .withDateRange(true)
            .withMultipleDatesSeparator(SEPARATOR)
            .withLocalization(localization -> localization
                .withDateFormat(DateFormatType.L, getPattern(SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale)))
                .withFormat(DateFormatType.L.name()));

        add(new TempusDominusBehavior(config));
    }

    private static String getPattern(DateFormat format) {
        return ((SimpleDateFormat) format).toLocalizedPattern();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> IConverter<C> getConverter(Class<C> type) {
        return (IConverter<C>) new AbstractConverter<Range>() {

            @Override
            protected Class<Range> getTargetType() {
                return Range.class;
            }

            @Override
            public Range convertToObject(String value, Locale locale) throws ConversionException {
                if (Strings.isEmpty(value)) {
                    return null;
                }

                LocalDateTime[] dates = Arrays.stream(value.split(SEPARATOR))
                    .map(convertToObject(locale))
                    .toArray(LocalDateTime[]::new);
                LocalDateTime start = get(dates, 0).orElse(null);
                LocalDateTime end = get(dates, 1).orElse(start);

                return new Range(start, end);
            }

            private static Function<String, LocalDateTime> convertToObject(Locale locale) {
                return date -> getConverter().convertToObject(date, locale);
            }

            private static <T> Optional<T> get(T[] dates, int i) {
                try {
                    return Optional.of(dates[i]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    return Optional.empty();
                }
            }

            @Override
            public String convertToString(Range value, Locale locale) {
                if (value == null) {
                    return null;
                }

                return Stream.of(value.start(), value.end())
                    .filter(Objects::nonNull)
                    .map(convertToString(locale))
                    .collect(Collectors.joining(SEPARATOR));
            }

            private static Function<LocalDateTime, String> convertToString(Locale locale) {
                return date -> getConverter().convertToString(date, locale);
            }

            private static IConverter<LocalDateTime> getConverter() {
                return LocalDateTimeConverter.instance();
            }
        };
    }


    private static class LocalDateTimeConverter extends AbstractJavaTimeConverter<LocalDateTime> {

        private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

        private LocalDateTimeConverter() {
        }

        @Override
        protected LocalDateTime createTemporal(TemporalAccessor temporalAccessor) {
            return LocalDateTime.from(temporalAccessor);
        }

        @Override
        protected DateTimeFormatter getDateTimeFormatter() {
            return DATE_TIME_FORMATTER;
        }

        @Override
        protected Class<LocalDateTime> getTargetType() {
            return LocalDateTime.class;
        }

        public static LocalDateTimeConverter instance() {
            return Holder.INSTANCE;
        }


        private static final class Holder {

            private static final LocalDateTimeConverter INSTANCE = new LocalDateTimeConverter();
        }
    }
}
