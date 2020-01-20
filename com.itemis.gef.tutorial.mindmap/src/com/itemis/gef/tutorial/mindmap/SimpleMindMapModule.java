package com.itemis.gef.tutorial.mindmap;

import java.util.Arrays;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdapterInjectionSupport;
import org.eclipse.gef.common.adapt.inject.AdapterInjectionSupport.LoggingMode;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.mvc.fx.MvcFxModule;
import org.eclipse.gef.mvc.fx.behaviors.HoverIntentBehavior;
import org.eclipse.gef.mvc.fx.behaviors.SelectionBehavior;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.handlers.BendFirstAnchorageOnSegmentHandleDragHandler;
import org.eclipse.gef.mvc.fx.handlers.BendOnSegmentDragHandler;
import org.eclipse.gef.mvc.fx.handlers.ConnectedSupport;
import org.eclipse.gef.mvc.fx.handlers.FocusAndSelectOnClickHandler;
import org.eclipse.gef.mvc.fx.handlers.HoverOnHoverHandler;
import org.eclipse.gef.mvc.fx.handlers.ResizeTransformSelectedOnHandleDragHandler;
import org.eclipse.gef.mvc.fx.handlers.ResizeTranslateFirstAnchorageOnHandleDragHandler;
import org.eclipse.gef.mvc.fx.handlers.RotateSelectedOnHandleDragHandler;
import org.eclipse.gef.mvc.fx.handlers.SnapToGeometry;
import org.eclipse.gef.mvc.fx.handlers.SnapToGrid;
import org.eclipse.gef.mvc.fx.handlers.TranslateSelectedOnDragHandler;
import org.eclipse.gef.mvc.fx.parts.CircleSegmentHandlePart;
import org.eclipse.gef.mvc.fx.parts.DefaultFocusFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultHoverFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultHoverIntentHandlePartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionHandlePartFactory;
import org.eclipse.gef.mvc.fx.parts.RectangleSegmentHandlePart;
import org.eclipse.gef.mvc.fx.parts.SquareSegmentHandlePart;
import org.eclipse.gef.mvc.fx.policies.BendConnectionPolicy;
import org.eclipse.gef.mvc.fx.policies.ResizePolicy;
import org.eclipse.gef.mvc.fx.policies.TransformPolicy;
import org.eclipse.gef.mvc.fx.providers.BoundsSnappingLocationProvider;
import org.eclipse.gef.mvc.fx.providers.CenterSnappingLocationProvider;
import org.eclipse.gef.mvc.fx.providers.DefaultAnchorProvider;
import org.eclipse.gef.mvc.fx.providers.GeometricOutlineProvider;
import org.eclipse.gef.mvc.fx.providers.ISnappingLocationProvider;
import org.eclipse.gef.mvc.fx.providers.ShapeBoundsProvider;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;
import org.eclipse.gef.mvc.fx.providers.TopLeftSnappingLocationProvider;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.google.inject.Provider;
import com.google.inject.multibindings.MapBinder;
import com.itemis.gef.tutorial.mindmap.behaviors.CreateFeedbackBehavior;
import com.itemis.gef.tutorial.mindmap.models.ItemCreationModel;
import com.itemis.gef.tutorial.mindmap.parts.MindMapConnectionPart;
import com.itemis.gef.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef.tutorial.mindmap.parts.MindMapPartsFactory;
import com.itemis.gef.tutorial.mindmap.parts.SimpleMindMapAnchorProvider;
import com.itemis.gef.tutorial.mindmap.parts.feedback.CreateFeedbackPartFactory;
import com.itemis.gef.tutorial.mindmap.parts.handles.CreateAndTranslateShapeOnDragHandler;
import com.itemis.gef.tutorial.mindmap.parts.handles.CreateCurveOnDragHandler;
import com.itemis.gef.tutorial.mindmap.parts.handles.DeleteMindMapNodeHandlePart;
import com.itemis.gef.tutorial.mindmap.parts.handles.MindMapHoverIntentHandlePartFactory;
import com.itemis.gef.tutorial.mindmap.parts.handles.MindMapSelectionHandlePartFactory;
import com.itemis.gef.tutorial.mindmap.parts.handles.RelocateLinkedOnDragHandler;
import com.itemis.gef.tutorial.mindmap.policies.CreateNewConnectionOnClickHandler;
import com.itemis.gef.tutorial.mindmap.policies.CreateNewNodeOnClickHandler;
import com.itemis.gef.tutorial.mindmap.policies.DeleteNodeOnHandleClickHandler;
import com.itemis.gef.tutorial.mindmap.policies.MindMapKeyHandler;
import com.itemis.gef.tutorial.mindmap.policies.ShowMindMapNodeContextMenuOnClickHandler;

/**
 * The Guice Module to configure our parts and behaviors.
 *
 */
public class SimpleMindMapModule extends MvcFxModule {

	@Override
	protected void bindAbstractContentPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindAbstractContentPartAdapters(adapterMapBinder);

		// binding the HoverOnHoverPolicy to every part
		// if a mouse is moving above a part it is set i the HoverModel
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);

		// add the focus and select policy to every part, listening to clicks
		// and changing the focus and selection model
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FocusAndSelectOnClickHandler.class);

	}

	protected void bindCircleSegmentHandlePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BendFirstAnchorageOnSegmentHandleDragHandler.class);
	}

	protected void bindCreateCurveHandlePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateCurveOnDragHandler.class);
	}

	protected void bindDeleteMindMapNodeHandlePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(DeleteNodeOnHandleClickHandler.class);
	}

	protected void bindFXRectangleSegmentHandlePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BendFirstAnchorageOnSegmentHandleDragHandler.class);
	}

	protected void bindGeometricCurvePartAdaptersInContentViewerContext(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// hover on hover
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);

		// geometry provider for selection feedback
		adapterMapBinder
				.addBinding(AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.to(GeometricOutlineProvider.class);
		// geometry provider for selection handles
		adapterMapBinder
				.addBinding(AdapterKey.role(DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER))
				.to(GeometricOutlineProvider.class);
		adapterMapBinder
				.addBinding(
						AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_LINK_FEEDBACK_GEOMETRY_PROVIDER))
				.to(GeometricOutlineProvider.class);
		// geometry provider for hover feedback
		adapterMapBinder.addBinding(AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER))
				.to(GeometricOutlineProvider.class);
		// geometry provider for focus feedback
		adapterMapBinder.addBinding(AdapterKey.role(DefaultFocusFeedbackPartFactory.FOCUS_FEEDBACK_GEOMETRY_PROVIDER))
				.to(GeometricOutlineProvider.class);

		// transaction policy for resize + transform
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ResizePolicy.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BendConnectionPolicy.class);

		// interaction handler to relocate on drag
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TranslateSelectedOnDragHandler.class);

		// drag individual segments
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BendOnSegmentDragHandler.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TransformPolicy.class);

//		// clickable area resizing
//		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ConnectionClickableAreaBehavior.class);

	}

	protected void bindGeometricShapePartAdapterInPaletteViewerContext(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateAndTranslateShapeOnDragHandler.class);
		adapterMapBinder.addBinding(AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER))
				.to(GeometricOutlineProvider.class);
	}

	protected void bindGeometricShapePartAdaptersInContentViewerContext(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// hover on hover
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);

		// geometry provider for selection feedback
		adapterMapBinder
				.addBinding(AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.toProvider(new Provider<ShapeBoundsProvider>() {
					@Override
					public ShapeBoundsProvider get() {
						return new ShapeBoundsProvider(0.5);
					}
				});
		// geometry provider for selection handles
		adapterMapBinder
				.addBinding(AdapterKey.role(DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER))
				.toProvider(new Provider<ShapeBoundsProvider>() {
					@Override
					public ShapeBoundsProvider get() {
						return new ShapeBoundsProvider(0.5);
					}
				});
		adapterMapBinder
				.addBinding(
						AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_LINK_FEEDBACK_GEOMETRY_PROVIDER))
				.to(GeometricOutlineProvider.class);
		// geometry provider for hover feedback
		adapterMapBinder.addBinding(AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeBoundsProvider.class);
		// geometry provider for hover handles
		adapterMapBinder
				.addBinding(AdapterKey.role(DefaultHoverIntentHandlePartFactory.HOVER_INTENT_HANDLES_GEOMETRY_PROVIDER))
				.to(ShapeBoundsProvider.class);
		// geometry provider for focus feedback
		adapterMapBinder.addBinding(AdapterKey.role(DefaultFocusFeedbackPartFactory.FOCUS_FEEDBACK_GEOMETRY_PROVIDER))
				.toProvider(new Provider<ShapeBoundsProvider>() {
					@Override
					public ShapeBoundsProvider get() {
						return new ShapeBoundsProvider(0.5);
					}
				});

		// register resize/transform policies (writing changes also to model)
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TransformPolicy.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ResizePolicy.class);

		// relocate on drag (including anchored elements, which are linked)
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(RelocateLinkedOnDragHandler.class);

		// bind dynamic anchor provider
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(DefaultAnchorProvider.class);

		// normalize connected on drag
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ConnectedSupport.class);

		adapterMapBinder.addBinding(AdapterKey.role(SnapToGrid.SOURCE_SNAPPING_LOCATION_PROVIDER))
				.to(TopLeftSnappingLocationProvider.class);
		adapterMapBinder.addBinding(AdapterKey.role(SnapToGeometry.SOURCE_SNAPPING_LOCATION_PROVIDER))
				.toInstance(ISnappingLocationProvider.union(
						Arrays.asList(new CenterSnappingLocationProvider(), new BoundsSnappingLocationProvider())));
		adapterMapBinder.addBinding(AdapterKey.role(SnapToGeometry.TARGET_SNAPPING_LOCATION_PROVIDER))
				.to(BoundsSnappingLocationProvider.class);
	}

	@Override
	protected void bindHoverHandlePartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role(HoverIntentBehavior.HOVER_INTENT_HANDLE_PART_FACTORY))
				.to(MindMapHoverIntentHandlePartFactory.class);
	}

	@Override
	protected void bindIContentPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// bind MindMapPartsFactory adapter to the content viewer
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(MindMapPartsFactory.class);
	}

	@Override
	protected void bindIRootPartAdaptersForContentViewer(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindIRootPartAdaptersForContentViewer(adapterMapBinder);

		// support creation of nodes via mouse click
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateNewNodeOnClickHandler.class);

		// adding the creation feedback behavior
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateFeedbackBehavior.class);

		// adding hotkeys handler
		// working create new node, new connection on hotkeys
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(MindMapKeyHandler.class);
	}

	@Override
	protected void bindIViewerAdaptersForContentViewer(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindIViewerAdaptersForContentViewer(adapterMapBinder);
		// add ItemCreationModel adapter binding
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ItemCreationModel.class);

		// binding the creation feedback part factory using the role that is
		// specified in the behavior
		AdapterKey<?> role = AdapterKey.role(CreateFeedbackBehavior.CREATE_FEEDBACK_PART_FACTORY);
		adapterMapBinder.addBinding(role).to(CreateFeedbackPartFactory.class);
	}

	/**
	 *
	 * @param adapterMapBinder
	 */
	protected void bindMindMapNodePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// bind anchor provider used to create the connection anchors
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(SimpleMindMapAnchorProvider.class);

		// bind a geometry provider, which is used in our anchor provider
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ShapeOutlineProvider.class);

		// provides a hover feedback to the shape, used by the HoverBehavior
		AdapterKey<?> role = AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(role).to(ShapeOutlineProvider.class);

		// provides a selection feedback to the shape
		role = AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(role).to(ShapeBoundsProvider.class);

		// support moving nodes via mouse drag
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TransformPolicy.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TranslateSelectedOnDragHandler.class);

		// specify the factory to create the geometry object for the selection
		// handles
		role = AdapterKey.role(DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(role).to(ShapeBoundsProvider.class);

		// support resizing nodes
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ResizePolicy.class);

		// support creation of connections
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateNewConnectionOnClickHandler.class);

		// bind the context menu policy to the part
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ShowMindMapNodeContextMenuOnClickHandler.class);

		// adding hotkeys handler
		// working delete node on hotkeys
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(MindMapKeyHandler.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BendConnectionPolicy.class);

//		// drag individual segments
//		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BendOnSegmentDragHandler.class);

	}

	protected void bindRectangleSegmentHandlePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(BendFirstAnchorageOnSegmentHandleDragHandler.class);
	}

	@Override
	protected void bindSelectionHandlePartFactoryAsContentViewerAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role(SelectionBehavior.SELECTION_HANDLE_PART_FACTORY))
				.to(MindMapSelectionHandlePartFactory.class);
	}

	protected void bindSquareSegmentHandlePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// single selection: resize relocate on handle drag without modifier
		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(ResizeTranslateFirstAnchorageOnHandleDragHandler.class);
		// rotate on drag + control
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(RotateSelectedOnHandleDragHandler.class);

		// multi selection: scale relocate on handle drag without modifier
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ResizeTransformSelectedOnHandleDragHandler.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ResizeTransformSelectedOnHandleDragHandler.class);
	}

	@Override
	protected void configure() {
		// start the default configuration
		super.configure();

		bindMindMapNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), MindMapNodePart.class));

		// with this binding we create the handles
		bindDeleteMindMapNodeHandlePartAdapters(
				AdapterMaps.getAdapterMapBinder(binder(), DeleteMindMapNodeHandlePart.class));

		bindGeometricCurvePartAdaptersInContentViewerContext(AdapterMaps.getAdapterMapBinder(binder(),
				MindMapConnectionPart.class, AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE)));

		// node selection handles and multi selection handles
		bindSquareSegmentHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), SquareSegmentHandlePart.class));

		// curve selection handles
		bindCircleSegmentHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), CircleSegmentHandlePart.class));

		bindRectangleSegmentHandlePartAdapters(
				AdapterMaps.getAdapterMapBinder(binder(), RectangleSegmentHandlePart.class));

//		bindGeometricShapePartAdapterInPaletteViewerContext(
//				AdapterMaps.getAdapterMapBinder(binder(), GeometricShapePart.class));

		bindCircleSegmentHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), CircleSegmentHandlePart.class));

		bindRectangleSegmentHandlePartAdapters(
				AdapterMaps.getAdapterMapBinder(binder(), RectangleSegmentHandlePart.class));

	}

	@Override
	protected void enableAdapterMapInjection() {
		install(new AdapterInjectionSupport(LoggingMode.PRODUCTION));
	}

}