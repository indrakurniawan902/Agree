package com.agree.ui.builder

import android.util.Log
import androidx.fragment.app.FragmentManager
import com.agree.ui.builder.widget.AgrFormContainer
import com.agree.ui.domain.model.DynamicFormSchema

class Schema(
    private val container: AgrFormContainer
) {

    /**
     * Schema List
     */
    var formSchema: MutableList<DynamicFormSchema> = mutableListOf()

    /**
     * Provide Fragment Manager from Fragment or Activities
     */
    var fragmentManager: FragmentManager? = null

    /**
     * Instance of Dynamic Form Engine
     */
    private val instance: AgrDynamicForm by lazy {
        AgrDynamicForm(container)
    }

    /**
     * Add Schema List into Current Schema into last position
     */
    fun addAll(schemas: List<DynamicFormSchema>) {
        formSchema.addAll(schemas)
    }

    /**
     * Add and Replace Current Schema List
     */
    fun addAndReplace(schemas: List<DynamicFormSchema>) {
        clearCurrentSchema()
        addAll(schemas)
    }

    /**
     * Add Schema List to Position
     */
    fun addAllToPosition(schemas: List<DynamicFormSchema>, position: Int) {
        formSchema.addAll(position, schemas)
    }

    /**
     * Add Schema to Last Position
     */
    fun addToLast(schema: DynamicFormSchema) {
        formSchema.add(schema)
    }

    /**
     * Add Schema to First Position
     */
    fun addToFirst(schema: DynamicFormSchema) {
        formSchema.add(0, schema)
    }

    /**
     * Adding schema to a specific position
     */
    fun addToPosition(schema: DynamicFormSchema, position: Int) {
        formSchema.add(position, schema)
    }

    /**
     * Update Schema in specific position
     */
    fun updateSchema(schema: DynamicFormSchema, position: Int) {
        runCatching {
            formSchema[position] = schema
        }.onFailure {
            Log.e("AgrDynamicForm", errorSchemaMessage)
        }
    }

    /**
     * Update Schema in specific ID
     */
    fun updateSchema(schema: DynamicFormSchema, id: String) {
        val index = formSchema.map { it.id }.indexOf(id)
        if (index >= 0) {
            formSchema[index] = schema
        } else {
            Log.e("AgrDynamicForm", errorSchemaMessage)
        }
    }

    /**
     * Update Schema position into specific position
     */
    fun moveToPosition(oldPosition: Int, newPosition: Int) {
        val data = formSchema[oldPosition]
        formSchema.removeAt(oldPosition)
        formSchema.add(newPosition, data)
    }

    /**
     * Update Schema position with ID into specific position
     */
    fun moveToPosition(id: String, newPosition: Int) {
        val index = formSchema.map { it.id }.indexOf(id)
        if (index >= 0) {
            val data = formSchema[index]
            formSchema.removeAt(index)
            formSchema.add(newPosition, data)
        } else {
            Log.e("AgrDynamicForm", errorSchemaMessage)
        }
    }

    /**
     * Remove Schema with specific ID
     */
    fun remove(id: String) {
        val index = formSchema.map { it.id }.indexOf(id)
        if (index >= 0) {
            formSchema.removeAt(index)
        } else {
            Log.e("AgrDynamicForm", errorSchemaMessage)
        }
    }

    /**
     * Remove Schema with specific position
     */
    fun removeAt(position: Int) {
        if (position < formSchema.size) {
            runCatching {
                formSchema.removeAt(position)
            }
        } else {
            Log.e("AgrDynamicForm", errorSchemaMessage)
        }
    }

    /**
     * Remove Schema bellow selected ID
     */
    fun removeAtBellow(id: String) {
        val index = formSchema.map { it.id }.indexOf(id)
        if (index >= 0) {
            if ((index + 1) < formSchema.size) {
                runCatching {
                    val removedList = formSchema.subList(index + 1, formSchema.size)
                    formSchema.removeAll(removedList)
                }
            } else {
                Log.e("AgrDynamicForm", "Schema: Position Out of Bound from existing Schema!")
            }
        } else {
            Log.e("AgrDynamicForm", errorSchemaMessage)
        }
    }

    /**
     * Clear Current Rendered Schema on Container
     */
    fun clearCurrentSchema() {
        formSchema.clear()
    }

    /**
     * Update view with updated Schemas
     */
    fun update(): AgrFormContainer {
        return instance.addAndReplaceFormSchema(formSchema)
            .setFragmentmanager(fragmentManager)
            .parseSchema()
            .update()
    }

    /**
     * Wrapping Up and Build Views with Schema
     */
    fun build(): AgrFormContainer {
        return instance.addAndReplaceFormSchema(formSchema)
            .setFragmentmanager(fragmentManager)
            .parseSchema()
            .build()
    }

    val errorSchemaMessage: String = "Schema: ID not found in existing Schema!"
}
