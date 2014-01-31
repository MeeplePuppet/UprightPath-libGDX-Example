package com.uprightpath.contagion.loader;

import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.uprightpath.contagion.logic.*;

public class WorldDefinitionManagerLoader
		extends
		AsynchronousAssetLoader<WorldDefinitionManager, WorldDefinitionManagerLoader.WorldDefinitionManagerAssetLoaderParameters> {
	private XmlReader reader = new XmlReader();
	private WorldDefinitionManager worldDefinitionManager = new WorldDefinitionManager();

	public WorldDefinitionManagerLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, WorldDefinitionManagerAssetLoaderParameters parameter) {
		try {
			Element element = reader.parse(file);
			for (Element parent : element.getChildrenByName("world")) {
				worldDefinitionManager.addDefinition(new WorldDefinition(parent));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public WorldDefinitionManager loadSync(AssetManager manager, String fileName, FileHandle file, WorldDefinitionManagerAssetLoaderParameters parameter) {
		return worldDefinitionManager;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, WorldDefinitionManagerAssetLoaderParameters parameter) {
		return new Array<AssetDescriptor>();
	}

	public class WorldDefinitionManagerAssetLoaderParameters extends
			AssetLoaderParameters<WorldDefinitionManager> {

	}
}
