package com.qcadoo.mes.core;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import org.junit.Test;

import com.qcadoo.mes.api.Entity;
import com.qcadoo.mes.internal.DefaultEntity;
import com.qcadoo.mes.model.FieldDefinition;
import com.qcadoo.mes.model.internal.FieldDefinitionImpl;
import com.qcadoo.mes.model.types.internal.IntegerType;
import com.qcadoo.mes.model.types.internal.StringType;
import com.qcadoo.mes.utils.ExpressionUtil;
import com.qcadoo.mes.view.components.grid.ColumnDefinition;

public class ExpressionUtilTest {

    @Test
    public void shouldReturnStringRepresentationOfOneFieldWithoutExpression() throws Exception {
        // given
        Entity entity = new DefaultEntity(1L);
        entity.setField("name", "Mr T");

        FieldDefinition fieldDefinition = new FieldDefinitionImpl("name").withType(new StringType());

        ColumnDefinition columnDefinition = new ColumnDefinition("col");
        columnDefinition.addField(fieldDefinition);

        // when
        String value = ExpressionUtil.getValue(entity, columnDefinition);

        // then
        assertEquals("Mr T", value);
    }

    @Test
    public void shouldReturnJoinedStringRepresentationsOfMultipleFieldWithoutExpression() throws Exception {
        // given
        Entity entity = new DefaultEntity(1L);
        entity.setField("name", "Mr T");
        entity.setField("age", 33);
        entity.setField("sex", "F");

        FieldDefinition fieldDefinitionName = new FieldDefinitionImpl("name").withType(new StringType());
        FieldDefinition fieldDefinitionAge = new FieldDefinitionImpl("age").withType(new IntegerType());
        FieldDefinition fieldDefinitionSex = new FieldDefinitionImpl("sex").withType(new StringType());

        ColumnDefinition columnDefinition = new ColumnDefinition("col");
        columnDefinition.addField(fieldDefinitionName);
        columnDefinition.addField(fieldDefinitionAge);
        columnDefinition.addField(fieldDefinitionSex);

        // when
        String value = ExpressionUtil.getValue(entity, columnDefinition);

        // then
        assertEquals("Mr T, 33, F", value);
    }

    @Test
    public void shouldGenerateValueOfTheSingleFieldColumn() throws Exception {
        // given
        Entity entity = new DefaultEntity(1L);
        entity.setField("name", "Mr T");

        FieldDefinition fieldDefinition = new FieldDefinitionImpl("name");

        ColumnDefinition columnDefinition = new ColumnDefinition("col");
        columnDefinition.addField(fieldDefinition);
        columnDefinition.setExpression("#name.toUpperCase()");

        // when
        String value = ExpressionUtil.getValue(entity, columnDefinition);

        // then
        assertEquals("MR T", value);
    }

    @Test
    public void shouldGenerateValueOfEmptyField() throws Exception {
        // given
        Entity entity = new DefaultEntity(1L);
        entity.setField("name", null);

        FieldDefinition fieldDefinition = new FieldDefinitionImpl("name");

        ColumnDefinition columnDefinition = new ColumnDefinition("col");
        columnDefinition.addField(fieldDefinition);
        columnDefinition.setExpression("#name");

        // when
        String value = ExpressionUtil.getValue(entity, columnDefinition);

        // then
        assertNull(value);
    }

    @Test
    public void shouldGenerateValueOfTheMultiFieldColumn() throws Exception {
        // given
        Entity entity = new DefaultEntity(1L);
        entity.setField("name", "Mr T");
        entity.setField("age", 33);
        entity.setField("sex", "F");

        FieldDefinition fieldDefinitionName = new FieldDefinitionImpl("name");
        FieldDefinition fieldDefinitionAge = new FieldDefinitionImpl("age");
        FieldDefinition fieldDefinitionSex = new FieldDefinitionImpl("sex");

        ColumnDefinition columnDefinition = new ColumnDefinition("col");
        columnDefinition.addField(fieldDefinitionName);
        columnDefinition.addField(fieldDefinitionAge);
        columnDefinition.addField(fieldDefinitionSex);
        columnDefinition.setExpression("#name + \" -> (\" + (#age+1) + \") -> \" + (#sex == \"F\" ? \"female\" : \"male\")");

        // when
        String value = ExpressionUtil.getValue(entity, columnDefinition);

        // then
        assertEquals("Mr T -> (34) -> female", value);
    }

    @Test
    public void shouldGenerateValueOfTheBelongsToColumn() throws Exception {
        // given
        Entity product = new DefaultEntity(1L);
        product.setField("name", "P1");

        Entity entity = new DefaultEntity(1L);
        entity.setField("product", product);

        FieldDefinition fieldDefinition = new FieldDefinitionImpl("product");

        ColumnDefinition columnDefinition = new ColumnDefinition("col");
        columnDefinition.addField(fieldDefinition);
        columnDefinition.setExpression("#product['name']");

        // when
        String value = ExpressionUtil.getValue(entity, columnDefinition);

        // then
        assertEquals("P1", value);
    }

}
