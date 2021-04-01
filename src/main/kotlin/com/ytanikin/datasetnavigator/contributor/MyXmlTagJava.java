package com.ytanikin.datasetnavigator.contributor;

import java.awt.Component;
import java.awt.Graphics;
import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.SystemDependent;
import org.jetbrains.annotations.SystemIndependent;
import org.picocontainer.PicoContainer;

import com.intellij.lang.Language;
import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.extensions.ExtensionsArea;
import com.intellij.openapi.extensions.PluginDescriptor;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.NotNullFactory;
import com.intellij.openapi.util.NotNullLazyValue;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.xml.XmlTagImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.messages.MessageBus;

public class MyXmlTagJava extends XmlTagImpl {

    public MyXmlTagJava() {

    }
    public MyXmlTagJava(XmlTagImpl xmlTag) {

    }

    public MyXmlTagJava(IElementType type) {
        super(type);
    }

    @Deprecated
    public VirtualFile getBaseDir() {
        return getProject().getBaseDir();
    }

    public @Nullable @SystemIndependent String getBasePath() {
        return getProject().getBasePath();
    }

    public @Nullable VirtualFile getProjectFile() {
        return getProject().getProjectFile();
    }

    public @Nullable @SystemIndependent String getProjectFilePath() {
        return getProject().getProjectFilePath();
    }

    public @Nullable @SystemDependent String getPresentableUrl() {
        return getProject().getPresentableUrl();
    }

    public @Nullable VirtualFile getWorkspaceFile() {
        return getProject().getWorkspaceFile();
    }

    public @NotNull String getLocationHash() {
        return getProject().getLocationHash();
    }

    public void save() {
        getProject().save();
    }

    public boolean isOpen() {
        return getProject().isOpen();
    }

    public boolean isInitialized() {
        return getProject().isInitialized();
    }

    public boolean isDefault() {
        return getProject().isDefault();
    }

    @Deprecated
    public @Nullable BaseComponent getComponent(@NotNull String name) {
        return getProject().getComponent(name);
    }

    public <T> T getComponent(@NotNull Class<T> interfaceClass) {
        return getProject().getComponent(interfaceClass);
    }

    @Deprecated
    public <T> T getComponent(@NotNull Class<T> interfaceClass, T defaultImplementationIfAbsent) {
        return getProject().getComponent(interfaceClass, defaultImplementationIfAbsent);
    }

    public boolean hasComponent(@NotNull Class<?> interfaceClass) {
        return getProject().hasComponent(interfaceClass);
    }

    @Deprecated
    public <T> T @NotNull [] getComponents(@NotNull Class<T> baseClass) {
        return getProject().getComponents(baseClass);
    }

    public @NotNull PicoContainer getPicoContainer() {
        return getProject().getPicoContainer();
    }

    public @NotNull MessageBus getMessageBus() {
        return getProject().getMessageBus();
    }

    public boolean isDisposed() {
        return getProject().isDisposed();
    }

    @Deprecated
    public boolean isDisposedOrDisposeInProgress() {
        return getProject().isDisposedOrDisposeInProgress();
    }

    @Deprecated
    public <T> T @NotNull [] getExtensions(
            @NotNull ExtensionPointName<T> extensionPointName) {
        return getProject().getExtensions(extensionPointName);
    }

    public @NotNull Condition<?> getDisposed() {
        return getProject().getDisposed();
    }

    @Deprecated
    public <T> T getService(@NotNull Class<T> serviceClass, boolean createIfNeeded) {
        return getProject().getService(serviceClass, createIfNeeded);
    }

    public <T> T getService(@NotNull Class<T> serviceClass) {
        return getProject().getService(serviceClass);
    }

    public <T> @Nullable T getServiceIfCreated(@NotNull Class<T> serviceClass) {
        return getProject().getServiceIfCreated(serviceClass);
    }

    public @NotNull ExtensionsArea getExtensionArea() {
        return getProject().getExtensionArea();
    }

    @ApiStatus.Internal
    public <T> T instantiateClass(@NotNull Class<T> aClass,
            @Nullable PluginId pluginId) {
        return getProject().instantiateClass(aClass, pluginId);
    }

    @ApiStatus.Internal
    public <T> T instantiateClassWithConstructorInjection(@NotNull Class<T> aClass,
            @NotNull Object key, @NotNull PluginId pluginId) {
        return getProject().instantiateClassWithConstructorInjection(aClass, key, pluginId);
    }

    @ApiStatus.Internal
    public void logError(@NotNull Throwable error,
            @NotNull PluginId pluginId) {
        getProject().logError(error, pluginId);
    }

    @ApiStatus.Internal
    public @NotNull RuntimeException createError(@NotNull Throwable error,
            @NotNull PluginId pluginId) {
        return getProject().createError(error, pluginId);
    }

    @ApiStatus.Internal
    public @NotNull RuntimeException createError(@NotNull String message,
            @NotNull PluginId pluginId) {
        return getProject().createError(message, pluginId);
    }

    @ApiStatus.Internal
    public <T> @NotNull T instantiateExtensionWithPicoContainerOnlyIfNeeded(@Nullable String name,
            @Nullable PluginDescriptor pluginDescriptor) {
        return getProject().instantiateExtensionWithPicoContainerOnlyIfNeeded(name, pluginDescriptor);
    }

    @ApiStatus.Internal
    @Deprecated
    public @NotNull <T> List<T> getComponentInstancesOfType(@NotNull Class<T> baseClass) {
        return getProject().getComponentInstancesOfType(baseClass);
    }

    @ApiStatus.Internal
    @Deprecated
    public @NotNull <T> List<T> getComponentInstancesOfType(@NotNull Class<T> baseClass,
            boolean createIfNotYet) {
        return getProject().getComponentInstancesOfType(baseClass, createIfNotYet);
    }

    public void dispose() {
        getProject().dispose();
    }

    public static boolean isComputed() {
        return ICON_PLACEHOLDER.isComputed();
    }

    public static @NotNull <T> NotNullLazyValue<T> createConstantValue(@NotNull T value) {
        return NotNullLazyValue.createConstantValue(value);
    }

    public static @NotNull <T> NotNullLazyValue<T> createValue(
            @NotNull NotNullFactory<? extends T> value) {
        return NotNullLazyValue.createValue(value);
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        getBaseIcon().paintIcon(c, g, x, y);
    }

    public int getIconWidth() {
        return getBaseIcon().getIconWidth();
    }

    public int getIconHeight() {
        return getBaseIcon().getIconHeight();
    }

    public static PsiElement @NotNull [] create(int count) {
        return ARRAY_FACTORY.create(count);
    }

    public static @NotNull Collection<Language> getRegisteredLanguages() {
        return Language.getRegisteredLanguages();
    }

    public static void unregisterLanguages(ClassLoader classLoader) {
        Language.unregisterLanguages(classLoader);
    }

    public static void unregisterLanguage(@NotNull Language language) {
        Language.unregisterLanguage(language);
    }

    @ApiStatus.Internal
    public void unregisterDialect(Language language) {
        getLanguage().unregisterDialect(language);
    }

    public static <T extends Language> T findInstance(@NotNull Class<T> klass) {
        return Language.findInstance(klass);
    }

    public static @NotNull Collection<Language> findInstancesByMimeType(
            @Nullable String mimeType) {
        return Language.findInstancesByMimeType(mimeType);
    }

    public String toString() {
        return getLanguage().toString();
    }

    public String @NotNull [] getMimeTypes() {
        return getLanguage().getMimeTypes();
    }

    public @NotNull String getID() {
        return getLanguage().getID();
    }

    public @Nullable LanguageFileType getAssociatedFileType() {
        return getLanguage().getAssociatedFileType();
    }

    @ApiStatus.Internal
    public @Nullable LanguageFileType findMyFileType(FileType[] types) {
        return getLanguage().findMyFileType(types);
    }

    public @Nullable Language getBaseLanguage() {
        return getLanguage().getBaseLanguage();
    }

    public @NotNull String getDisplayName() {
        return getLanguage().getDisplayName();
    }

    public boolean is(Language another) {
        return getLanguage().is(another);
    }

    public boolean isCaseSensitive() {
        return getLanguage().isCaseSensitive();
    }

    public boolean isKindOf(Language another) {
        return getLanguage().isKindOf(another);
    }

    public boolean isKindOf(@NotNull String anotherLanguageId) {
        return getLanguage().isKindOf(anotherLanguageId);
    }

    public @NotNull List<Language> getDialects() {
        return getLanguage().getDialects();
    }

    public static @Nullable Language findLanguageByID(String id) {
        return Language.findLanguageByID(id);
    }

    public String getUserDataString() {
        return getLanguage().getUserDataString();
    }

    public void copyUserDataTo(@NotNull UserDataHolderBase other) {
        getLanguage().copyUserDataTo(other);
    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return getLanguage().getUserData(key);
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {
        getLanguage().putUserData(key, value);
    }

    public <T> T getCopyableUserData(@NotNull Key<T> key) {
        return getLanguage().getCopyableUserData(key);
    }

    public <T> void putCopyableUserData(@NotNull Key<T> key, T value) {
        getLanguage().putCopyableUserData(key, value);
    }

    public <T> boolean replace(@NotNull Key<T> key, @Nullable T oldValue,
            @Nullable T newValue) {
        return getLanguage().replace(key, oldValue, newValue);
    }

    public <T> @NotNull T putUserDataIfAbsent(@NotNull Key<T> key,
            @NotNull T value) {
        return getLanguage().putUserDataIfAbsent(key, value);
    }

    public void copyCopyableDataTo(@NotNull UserDataHolderBase clone) {
        getLanguage().copyCopyableDataTo(clone);
    }

    public boolean isUserDataEmpty() {
        return getLanguage().isUserDataEmpty();
    }
}
