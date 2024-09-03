package com.verinite.cla.model;

public class MethodDetails {

        private final String annotationName;
        private final String annotationValue;
        private final String methodBody;

        public MethodDetails(String annotationName, String annotationValue, String methodBody) {
            this.annotationName = annotationName;
            this.annotationValue = annotationValue;
            this.methodBody = methodBody;
        }

        public String getAnnotationName() {
            return annotationName;
        }

        public String getAnnotationValue() {
            return annotationValue;
        }

        public String getMethodBody() {
            return methodBody;
        }
}
